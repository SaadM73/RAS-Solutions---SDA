package Member;

import Staff.*;
import application.SQLPersistenceHandler;

import java.sql.SQLException;
import java.util.List;

import Admin.*;
public class Payment {
    private int paymentId;
    private int memberId;
    private int bookingId;
    private double amount;
    private String status;

    // Constructor
    public Payment(int paymentId, int memberId, int bookingId, double amount, String status) {
        this.paymentId = paymentId;
        this.memberId = memberId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.status = status;
    }

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public static List<Payment> getAllPayments() throws SQLException
    {
    	return SQLPersistenceHandler.getInstance().getAllPayments();
    }
    
    public static boolean processPayment(int id)
    {
    	return SQLPersistenceHandler.getInstance().processPayment(id);
    }
    
    public static boolean isValidPaymentId(int id)
    {
    	return SQLPersistenceHandler.getInstance().isValidPaymentId(id);
    }
    
    public static Payment getPaymentByID(int id) throws SQLException
    {
    	return SQLPersistenceHandler.getInstance().getPaymentById(id);
    }
}
