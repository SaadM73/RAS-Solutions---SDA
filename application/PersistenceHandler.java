package application;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import Admin.Facility;
import Admin.FacilityMaintenance;
import Member.Member;
import Member.Membership;
import Member.Payment;
import Staff.Booking;
import Staff.Staff;


// Abstract class for database operations
public interface PersistenceHandler 
{
    
    // Member-related operations
    public abstract int validateMemberLogin(String email, String password);
    public abstract int insertMember(Member member);
    public abstract boolean deleteMember(int memberId);
    public abstract boolean updateMember(String memberName, String email, String phoneNumber, String password);
    public abstract List<Member> getAllMembers();

    // Membership-related operations
    public abstract boolean cancelMembership(int memberId);
    public abstract Membership getMembershipDetails(int memberId);
    public Member getMemberByEmailAndPassword(String email, String password);

    // Payment-related operations
    public abstract List<Payment> getAllPayments() throws SQLException;
    public abstract boolean processPayment(int paymentId);

    // Refund-related operations
    public abstract List<Payment> getRefundablePayments() throws SQLException;
    public abstract boolean requestRefund(int paymentId, double amount, String reason);
    
 // Booking
    public abstract int FacilityNametoID(String name);
    public abstract void insertBooking(Booking booking);
    public abstract List<Booking> getAllBookingsByMember(int memberId);
    public abstract boolean cancelBooking(int bookingId);
    
    public abstract boolean validateStaffLogin(String email, String password);
    public abstract void insertStaff(Staff staff);
    public boolean updateStaff(String memberName, String email, String phoneNumber, String password, String role);
    public abstract boolean deleteStaff(int staffId);
    public List<Staff> getAllStaff();
    
    public abstract boolean validateAdminLogin(String email, String password);
    public abstract void insertFacility(Facility facility);
    public abstract void maintainFacility(FacilityMaintenance fm);
    public abstract boolean deleteFacility(int facilityId);
    
    
    // Connection management


    public abstract void closeConnection();
}