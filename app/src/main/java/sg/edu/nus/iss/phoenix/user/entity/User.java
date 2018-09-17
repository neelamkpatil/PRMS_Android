package sg.edu.nus.iss.phoenix.user.entity;

import java.util.ArrayList;

/**
 * Created by wangzuxiu on 17/9/18.
 */

public class User {
    private String id;
    private String password;
    private String name;
    private ArrayList<Role> roles = new ArrayList<Role>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    public User(String idIn, String password, String name, ArrayList<Role> roles) {
        this.name=name;
        this.password=password;
        this.roles=roles;
        this.id = idIn;

    }

}
