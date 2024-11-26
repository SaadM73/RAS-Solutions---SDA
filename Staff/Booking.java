package Staff;

import Member.*;
import application.SQLPersistenceHandler;
import Admin.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class Booking {
    private int bookingId;
    private int memberId;
    private int facilityId;
    private Date bookingDate;
    private Time timeSlot;
    private String status;

    public Booking(int bookingId, int memberId, int facilityId, Date bookingDate, Time timeSlot, String status) {
        this.bookingId = bookingId;
        this.memberId = memberId;
        this.facilityId = facilityId;
        this.bookingDate = bookingDate;
        this.timeSlot = timeSlot;
        this.status = status;
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Time getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(Time timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public static void insertBooking(Booking b)
    {
    	SQLPersistenceHandler.getInstance().insertBooking(b);
    }
    
    public static boolean cancelBooking(int id)
    {
    	return SQLPersistenceHandler.getInstance().cancelBooking(id);
    }
    
    public static List<Booking> getAllBookings() throws SQLException
    {
    	return SQLPersistenceHandler.getInstance().getAllBookings();
    }
    
    public static List<Booking> getAllBookingsByMember(int id)
    {
    	return SQLPersistenceHandler.getInstance().getAllBookingsByMember(id);
    }
    
    public static List<Booking> getBookingsByFacility(int id)
    {
    	return SQLPersistenceHandler.getInstance().getBookingsByFacility(id);
    }
    
}