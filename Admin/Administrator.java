package Admin;

import Member.*;
import Staff.*;
import application.SQLPersistenceHandler;

public class Administrator {
    private int adminId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;

    public Administrator(int adminId, String name, String email, String phoneNumber, String password) {
        this.adminId = adminId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getters and Setters
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public static boolean validateAdminLogin(String email, String pass)
    {
    	return SQLPersistenceHandler.getInstance().validateAdminLogin(email, pass);
    }
}