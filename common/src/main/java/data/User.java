package data;

import java.io.Serializable;

public class User implements Serializable, Comparable<User> {

    private String login;
    private String password;
    private String salt;
    private boolean isAuthenticated = false;

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public int compareTo(User user){
        return login.compareTo(user.login);
    }
}
