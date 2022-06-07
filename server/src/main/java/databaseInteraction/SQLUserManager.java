package databaseInteraction;

import data.User;

import java.beans.Encoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.*;

public class SQLUserManager {
    private static final String PEPPER = "(go_#+S)+Je#";
    private Collection<String> usersLoginSet;
    private final Connection connection;

     SQLUserManager(Connection connection) throws SQLException {
        this.connection = connection;
        CreateSQLTable.createUserTable(connection);
        deserialize();
    }

    private void deserialize() throws SQLException {
        usersLoginSet = Collections.synchronizedSet(new HashSet<>());
        Statement stat = connection.createStatement();
        ResultSet res = stat.executeQuery("SELECT login FROM users");
        while (res.next()){
            usersLoginSet.add(res.getString("login"));
        }
    }

    private static String encodeHashWithSalt(String message, String salt){
        try{
            Base64.Encoder encoder =  Base64.getEncoder();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest((PEPPER + message + salt).getBytes(StandardCharsets.UTF_8));
            return encoder.encodeToString(hash);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }

    public User register(User user){
        if (addElement(user) == true){
            return user;
        }
        return null;
    }

    public boolean checkIn(User user){
        return usersLoginSet.contains(user.getLogin());
    }

    public boolean addElement(User user) {
        final int saltBytes = 7;
        final int indexPassword = 2;
        String insertUser = "INSERT INTO users VALUES (" +
                " ?,?,?) RETURNING login";
        Base64.Encoder encoder = Base64.getEncoder();
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltBytes];
        random.nextBytes(salt);
        String saltStr = encoder.encodeToString(salt);
        String hashStr = encodeHashWithSalt(user.getPassword(), saltStr);
        user.setSalt(saltStr);
        try (PreparedStatement statUser = connection.prepareStatement(insertUser)) {
            prepareStatUser(statUser, user);
            statUser.setString(indexPassword, hashStr);
            ResultSet resUser = statUser.executeQuery();
            resUser.next();
            if (resUser.getString("login").equals(user.getLogin())){
                usersLoginSet.add(user.getLogin());
                return true;
            }
            return false;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private void prepareStatUser(PreparedStatement stat, User user) throws SQLException {
        int indexColumn = 1;
        stat.setString(indexColumn++, user.getLogin());
        stat.setString(indexColumn++, user.getPassword());
        stat.setString(indexColumn, user.getSalt());
    }

    public boolean authenticate(User user){
        final String findUserQuery = "SELECT * FROM users WHERE login=?";
        if (Objects.isNull(user)){
            return false;
        }
        if (usersLoginSet.contains(user.getLogin())){
            try(PreparedStatement stat = connection.prepareStatement(findUserQuery)){
                stat.setString(1, user.getLogin());
                ResultSet res = stat.executeQuery();
                res.next();
                String realPasswordHashed = res.getString("password");
                String passwordHashed = encodeHashWithSalt(user.getPassword(), res.getString("salt"));
                if (passwordHashed.equals(realPasswordHashed)){
                    return true;
                }
            } catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
