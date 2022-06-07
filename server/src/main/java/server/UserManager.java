package server;

import data.User;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users = new ArrayList<>();

    public UserManager(){}



    public int evaluateUser(User user){
        for (User u:users){
            if (u.getLogin().equals(user.getLogin()) && u.getPassword().equals(user.getPassword())){
                return 0;
            }
            else if (u.getLogin().equals(user.getLogin())){
                return 1;
            }
        }
        addToUserList(user);
        return 2;
    }

    public void addToUserList(User user){
        users.add(user);
    }

}
