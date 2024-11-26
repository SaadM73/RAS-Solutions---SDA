package Member;

import application.SQLPersistenceHandler;

public class Member 
{
    private int memberId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String membershipType;

    public Member(int memberId, String name, String email, String password, String phoneNumber, String membershipType) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.membershipType = membershipType;
    }

    // Getters and Setters
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType)
    {
        this.membershipType = membershipType;
    }
    
    public static void register(Member m)
    {   	
    	
    	int id = SQLPersistenceHandler.getInstance().insertMember(m);
    	Membership.insertMembership(id);
    }
    
    public static boolean isEmailAlreadyRegistered(String email)
    {
    	return SQLPersistenceHandler.getInstance().getAllMembers().stream()
                .anyMatch(member -> member.getEmail().equalsIgnoreCase(email));
    }
    
    public static Member Login(String email,String  password)
    {
    	Member m = SQLPersistenceHandler.getInstance().getMemberByEmailAndPassword(email, password);
    	return m;
    }
    
    public static boolean isValidMemberId(int id)
    {
    	return SQLPersistenceHandler.getInstance().isValidMemberId(id);
    }
    
    public static Member getMemberByID(int id)
    {
    	return SQLPersistenceHandler.getInstance().getMemberById(id);
    }
    
    public static boolean updateMember(String name, String email, String number, String password)
    {
    	return SQLPersistenceHandler.getInstance().updateMember(name, email, number, password);
    }
}