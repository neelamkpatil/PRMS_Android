package sg.edu.nus.iss.phoenix.user.entity;

/**
 * Created by wangzuxiu on 17/9/18.
 * Updated by Surbhi
 */

public class Role {
    private String role;
    private String accessPrivilege;
    private static final String DELIMITER = ":";

    public Role(String role, String accessPrivilege) {
        this.role = role;
        this.accessPrivilege = accessPrivilege;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccessPrivilege() {
        return accessPrivilege;
    }

    public void setAccessPrivilege(String accessPrivilege) {
        this.accessPrivilege = accessPrivilege;
    }


}
