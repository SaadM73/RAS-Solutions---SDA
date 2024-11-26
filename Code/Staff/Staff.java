package Staff;

import Member.*;
import application.SQLPersistenceHandler;

import java.util.List;

import Admin.*;
public class Staff {
    private int staffId;
    private String name;
    private String role;
    private String email;
    private String phoneNumber;
    private String password;

    public Staff(int staffId, String name, String role, String email, String phoneNumber, String password) {
        this.staffId = staffId;
        this.name = name;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getters and Setters
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
    
    public static void insertStaff(Staff staff)
    {
    	SQLPersistenceHandler.getInstance().insertStaff(staff);
    }
    
    public static List<Staff> getAllStaff()
    {
    	return SQLPersistenceHandler.getInstance().getAllStaff();
    }
    
    public static boolean isEmailAlreadyRegistered(String email)
    {
    	return SQLPersistenceHandler.getInstance().getAllStaff().stream()
                .anyMatch(member -> member.getEmail().equalsIgnoreCase(email));
    }
    
    public static boolean isValidStaffId(int id)
    {
    	return SQLPersistenceHandler.getInstance().isValidStaffId(id);
    }
    
    public static boolean validateStaffLogin(String email, String pass)
    {
    	return SQLPersistenceHandler.getInstance().validateStaffLogin(email, pass);
    }
    
    public static void deleteStaff(int id)
    {
    	SQLPersistenceHandler.getInstance().deleteStaff(id);
    }
    
    public static boolean updateStaff(String name, String email, String number, String pass, String role)
    {
    	return SQLPersistenceHandler.getInstance().updateStaff(name, email, number, pass, role);
    }
}