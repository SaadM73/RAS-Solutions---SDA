package application;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Admin.Facility;
import Admin.FacilityMaintenance;
import Member.Member;
import Member.Membership;
import Member.Payment;
import Staff.Booking;
import Staff.Staff;


public class SQLPersistenceHandler implements PersistenceHandler 
{

    private Connection connection;
    private static SQLPersistenceHandler instance = new SQLPersistenceHandler();
    

    // Constructor to establish the database connection
    private SQLPersistenceHandler() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "rehan12345");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static SQLPersistenceHandler getInstance()
    {
    	return instance;
    }

    // Validate member login
    @Override
    public int validateMemberLogin(String email, String password) {
        String query = "SELECT member_id FROM Member WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("member_id"); // Return Member ID if credentials are valid
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 for invalid credentials
    }

    public String loadMemberName(String memberEmail) 
    {
        String query = "SELECT name FROM Member WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, memberEmail);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String memberName = resultSet.getString("name"); 
                    return memberName;
                   // welcomeLabel.setText("Welcome, " + memberName + "!");
                } else {
                   // welcomeLabel.setText("Welcome, Member!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return " ";
        
    }
    
    // Insert a new member
    @Override
    public int insertMember(Member member) {
        int generatedId = -1; // Default value if ID is not retrieved
        try {
            String query = "INSERT INTO Member (name, email, password, phone_number, membership_type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getEmail());
            stmt.setString(3, member.getPassword());
            stmt.setString(4, member.getPhoneNumber());
            stmt.setString(5, member.getMembershipType());

            // Execute the update
            stmt.executeUpdate();

            // Retrieve the auto-generated keys
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                generatedId = keys.getInt(1); // Assuming the auto-generated key is the first column
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public void insertMembership(int memberId) {
        try {
            // SQL query to insert membership
            String query = """
                INSERT INTO Membership (member_id, start_date, end_date, status)
                VALUES (?, ?, ?, ?)
            """;

            // Current date as start_date
            LocalDateTime startDate = LocalDateTime.now();
            LocalDateTime endDate = startDate.plusYears(1); // End date is one year from the start date

            // Prepare the statement
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, memberId);
            stmt.setTimestamp(2, Timestamp.valueOf(startDate));
            stmt.setTimestamp(3, Timestamp.valueOf(endDate));
            stmt.setString(4, "Active"); // Default status

            // Execute the insert
            stmt.executeUpdate();

            System.out.println("Membership successfully added for member_id: " + memberId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to insert membership for member_id: " + memberId);
        }
    }

    // Delete a member
    @Override
    public boolean deleteMember(int memberId) {
        try {
            String query = "DELETE FROM Member WHERE member_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, memberId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update member details
    @Override
    public boolean updateMember(String memberName, String email, String phoneNumber, String password) {
        try {
            String query = "UPDATE Member SET email = ?, phone_number = ?, password = ? WHERE name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, password);
            stmt.setString(4, memberName);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all members
    @Override
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        try {
            String query = "SELECT * FROM Member";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                members.add(new Member(
                        rs.getInt("member_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("membership_type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    

    public List<Membership> getAllMemberships() {
        List<Membership> memberships = new ArrayList<>();
        String query = "SELECT * FROM Membership";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                memberships.add(new Membership(
                    rs.getInt("membership_id"),
                    rs.getInt("member_id"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("end_date").toLocalDate(),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return memberships;
    }
    
    public List<Membership> getMembershipsByMemberId(int memberId) {
        List<Membership> memberships = new ArrayList<>();
        String query = "SELECT * FROM Membership WHERE member_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, memberId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    memberships.add(new Membership(
                        rs.getInt("membership_id"),
                        rs.getInt("member_id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getString("status")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return memberships;
    }
    
    // Cancel membership
    @Override
    public boolean cancelMembership(int memberId) {
        try {
            String query = "UPDATE Membership SET status = 'Cancelled' WHERE member_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, memberId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get membership details
    @Override
    public Membership getMembershipDetails(int memberId) {
        try {
            String query = "SELECT * FROM Membership WHERE member_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Membership(
                        rs.getInt("membership_id"),
                        rs.getInt("member_id"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getDate("end_date").toLocalDate(),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public Member getMemberByEmailAndPassword(String email, String password) {
        try {
            String query = "SELECT * FROM Member WHERE email = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Member(
                    rs.getInt("member_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone_number"),
                    rs.getString("membership_type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Member getMemberByID(int memberid) {
        try {
            String query = "SELECT * FROM Member WHERE member_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, memberid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Member(
                    rs.getInt("member_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone_number"),
                    rs.getString("membership_type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<Payment> getAllPayments() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT payment_id, member_id, booking_id, amount, status FROM Payment where status = 'Pending'";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                payments.add(new Payment(
                    rs.getInt("payment_id"),      // Payment ID
                    rs.getInt("member_id"),       // Member ID
                    rs.getInt("booking_id"),      // Booking ID
                    rs.getDouble("amount"),       // Amount
                    rs.getString("status")        // Status
                ));
            }
        }
        return payments;
    }


    // Process payment
    @Override
    public boolean processPayment(int paymentId) {
        try {
            String query = "UPDATE Payment SET status = 'Paid' WHERE payment_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, paymentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get refundable payments
    @Override
    public List<Payment> getRefundablePayments() throws SQLException {
    	List<Payment> payments = new ArrayList<>();
        String query = "SELECT payment_id, member_id, booking_id, amount, status FROM Payment where status = 'Paid'";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                payments.add(new Payment(
                    rs.getInt("payment_id"),      // Payment ID
                    rs.getInt("member_id"),       // Member ID
                    rs.getInt("booking_id"),      // Booking ID
                    rs.getDouble("amount"),       // Amount
                    rs.getString("status")        // Status
                ));
            }
        }
        return payments;
    }

    public boolean isValidPaymentId(int paymentid)
    {
    	String query = "SELECT COUNT(*) FROM Payment WHERE payment_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) 
        {
            statement.setInt(1, paymentid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Returns true if count > 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Request refund
   
    
    @Override
    public boolean requestRefund(int paymentId, double amount, String reason) {
    	String query = "INSERT INTO Refund (payment_id, amount, reason, status) VALUES (?, ?, ?, 'Pending')";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, paymentId);
            stmt.setDouble(2, amount); // Set the refund amount
            stmt.setString(3, reason);
            stmt.executeUpdate();        
            return stmt.executeUpdate() > 0;
        } 
        
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Payment getPaymentById(int paymentId) throws SQLException {
        String query = "SELECT payment_id, booking_id,member_id,  amount, status FROM Payment WHERE payment_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, paymentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Payment(
                        rs.getInt("payment_id"),
                        rs.getInt("booking_id"),
                        rs.getInt("member_id"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                    );
                } else {
                    // Handle case when no payment is found
                    throw new SQLException("No payment found with ID: " + paymentId);
                }
            }
        }
    }

    
    @Override
    public int FacilityNametoID(String name) {
        int facilityID = -1; // Default value if no ID is found
        String query = "SELECT facility_id FROM Facility WHERE name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) 
        {

            // Set the parameter for the query
            stmt.setString(1, name);

            // Execute the query and process the result
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    facilityID = rs.getInt("facility_id");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching facility ID: " + e.getMessage());
        }
        System.out.println(facilityID);
        return facilityID;
    }

    public boolean isValidMemberId(int memberId)
    {
    	String query = "SELECT COUNT(*) FROM Member WHERE member_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) 
        {
            statement.setInt(1, memberId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Returns true if count > 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public void insertBooking(Booking booking) {
        
        try 
        {        	
        	String query = "INSERT INTO Booking (member_id, facility_id, booking_date, time_slot, status) VALUES (?, ?, ?, ?, ?)";
        	PreparedStatement stmt = connection.prepareStatement(query); 

            stmt.setInt(1, booking.getMemberId());
            stmt.setInt(2, booking.getFacilityId());
            stmt.setDate(3, booking.getBookingDate());
            stmt.setTime(4, booking.getTimeSlot());
            stmt.setString(5, booking.getStatus());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) 
            {
                System.out.println("Booking inserted successfully.");
                
            }
        } catch (SQLException e) {
            System.err.println("Error inserting booking: " + e.getMessage());
        }
    }


    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String query = "SELECT booking_id, member_id, facility_id, booking_date, time_slot, status FROM Booking";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("member_id"),
                    rs.getInt("facility_id"), // Directly using facility_id
                    rs.getDate("booking_date"),
                    rs.getTime("time_slot"),
                    rs.getString("status")
                ));
            }
        }
        return bookings;
    }


    
    
    @Override
    public List<Booking> getAllBookingsByMember(int memberId) {
        List<Booking> bookings = new ArrayList<>();
        try {
            String query = "SELECT * FROM Booking WHERE member_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("member_id"),
                    rs.getInt("facility_id"),
                    rs.getDate("booking_date"),
                    rs.getTime("time_slot"),
                    rs.getString("status")
                );
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        try {
            String query = "UPDATE Booking SET status = 'Cancelled' WHERE booking_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
 // Validate staff login
 // Validate staff login
    @Override
    public boolean validateStaffLogin(String email, String password) {
        try {
            String query = "SELECT * FROM Staff WHERE email = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public void insertStaff(Staff staff) {
        try {
            String query = "INSERT INTO Staff (name, role, email, phone_number, password ) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, staff.getName());
            stmt.setString(2, staff.getRole());
            stmt.setString(3, staff.getEmail());
            stmt.setString(5, staff.getPassword());
            stmt.setString(4, staff.getPhoneNumber());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean updateStaff(String staffName, String email, String phoneNumber, String password, String role) {
        try {
            String query = "UPDATE Staff SET email = ?, phone_number = ?, password = ?, role = ? WHERE name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, password);
            stmt.setString(4, role);
            stmt.setString(5, staffName);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Delete staff
    @Override
    public boolean deleteStaff(int staffId) {
        try {
            String query = "DELETE FROM Staff WHERE staff_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, staffId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<Staff> getAllStaff()
    {
        List<Staff> staffList = new ArrayList<>();
        try {
            String query = "SELECT * FROM Staff";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                staffList.add(new Staff(
                    rs.getInt("staff_id"),
                    rs.getString("name"),
                    rs.getString("role"),
                    rs.getString("email"),
                    rs.getString("phone_number"),
                    rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }
    
    @Override
    public boolean validateAdminLogin(String email, String password) 
    {
        try {
            String query = "SELECT * FROM Admin WHERE email = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public void insertFacility(Facility facility) {
        try {
            String query = "INSERT INTO Facility (name, type, availability_status, capacity) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, facility.getName());
            stmt.setString(2, facility.getType());
            stmt.setString(3, facility.getAvailabilityStatus());
            stmt.setInt(4, facility.getCapacity());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void maintainFacility(FacilityMaintenance fm)
    {
    	try
    	{
    		String query = "INSERT INTO Facility_Maintenance (facility_id, scheduled_date, task_description, assigned_staff_id, status) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement stmt = connection.prepareStatement(query);
	        stmt.setInt(1, fm.getFacilityId());
            stmt.setDate(2, fm.getScheduledDate());
            stmt.setString(3, fm.getTaskDescription());
            stmt.setInt(4, fm.getAssignedStaffId());
            stmt.setString(5, fm.getStatus());
            stmt.executeUpdate();
	        
    	}
    	
    	catch (SQLException e)
    	{
            e.printStackTrace();
        }
    }
    
    public boolean isValidFacilityId(int facilityid)
    {
    	String query = "SELECT COUNT(*) FROM Facility WHERE facility_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) 
        {
            statement.setInt(1, facilityid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Returns true if count > 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean isValidStaffId(int staffid)
    {
    	String query = "SELECT COUNT(*) FROM Staff WHERE staff_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) 
        {
            statement.setInt(1, staffid);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Returns true if count > 0
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public boolean deleteFacility(int facilityId) 
    {
    	try
    	{
	    	String query = "DELETE FROM Facility WHERE facility_id = ?";
	        PreparedStatement stmt = connection.prepareStatement(query);
	        stmt.setInt(1, facilityId);
	        return stmt.executeUpdate() > 0;
    	}    	
    	catch (SQLException e)
    	{
            e.printStackTrace();
            return false;
        }

    }
    
    public List<Facility> getAllFacilities() throws SQLException {
        List<Facility> facilities = new ArrayList<>();
        String query = "SELECT facility_id, name, type, availability_status, capacity FROM Facility";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Facility facility = new Facility(
                    rs.getInt("facility_id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getString("availability_status"),
                    rs.getInt("capacity")
                );
                facilities.add(facility);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching facilities: " + e.getMessage());
            throw e;
        }
        return facilities;
    }
    
    public Facility getFacilityById(int facilityId) throws SQLException {
        String query = "SELECT facility_id, name, type, availability_status, capacity FROM Facility WHERE facility_id = ?";
        Facility facility = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, facilityId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    facility = new Facility(
                        rs.getInt("facility_id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("availability_status"),
                        rs.getInt("capacity")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching facility by ID: " + e.getMessage());
            throw e;
        }
        return facility;
    }

    public List<Member> getMembersWithActiveMemberships() {
        String query = """
            SELECT member_id, name, email, password, phone_number, membership_type 
            FROM Member 
            WHERE member_id IN (SELECT member_id FROM Membership WHERE status = 'Active')
        """;

        List<Member> activeMembers = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Member member = new Member(
                    resultSet.getInt("member_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("phone_number"),
                    resultSet.getString("membership_type")
                );
                activeMembers.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activeMembers;
    }
    
    public Member getMemberById(int memberId)
    {
        String query = """
            SELECT m.member_id, m.name, m.email, m.password, m.phone_number, mem.status AS membershipType
            FROM Member m
            JOIN Membership mem ON m.member_id = mem.member_id
            WHERE m.member_id = ?
        """;

        try (PreparedStatement statement = connection.prepareStatement(query))
        {

            statement.setInt(1, memberId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                {
                    return new Member(
                        resultSet.getInt("member_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("phone_number"),
                        resultSet.getString("membershipType")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public List<Booking> getBookingsByFacility(int facilityId) {
        String query = """
            SELECT b.booking_id, b.member_id, b.facility_id, m.name AS member_name, b.booking_date, b.time_slot, b.status
            FROM Booking b
            JOIN Member m ON b.member_id = m.member_id
            WHERE b.facility_id = ?
        """;

        List<Booking> bookings = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the facility ID parameter
            statement.setInt(1, facilityId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Booking booking = new Booking(
                        resultSet.getInt("booking_id"),
                        resultSet.getInt("member_id"),
                        resultSet.getInt("facility_id"),
                        resultSet.getDate("booking_date"),
                        resultSet.getTime("time_slot"),
                        resultSet.getString("status")
                    );
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }
    
    public List<Facility> getFacilitiesOrderedByAvailability() {
        String query = """
            SELECT facility_id, name, type, availability_status, capacity
            FROM Facility
            ORDER BY availability_status DESC
        """;

        List<Facility> facilities = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Facility facility = new Facility(
                    resultSet.getInt("facility_id"),
                    resultSet.getString("name"),
                    resultSet.getString("type"),
                    resultSet.getString("availability_status"),
                    resultSet.getInt("capacity")
                );
                facilities.add(facility);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return facilities;
    }
    
    // Close the database connection
    @Override
    public void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}