package Member;

import Staff.*;
import application.SQLPersistenceHandler;
import Admin.*;
import java.time.LocalDateTime;

public class Refund 
{
    private int refundId;
    private int paymentId;
    private double amount;
    private String reason;
    private LocalDateTime refundDate;

    public Refund(int refundId, int paymentId, double amount, String reason, LocalDateTime refundDate) 
    {
        this.refundId = refundId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.reason = reason;
        this.refundDate = refundDate;
    }

    // Getters and setters
    public int getRefundId() {
        return refundId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public LocalDateTime getRefundDate() {
        return refundDate;
    }

    @Override
    public String toString() {
        return "Refund{" +
                "refundId=" + refundId +
                ", paymentId=" + paymentId +
                ", amount=" + amount +
                ", reason='" + reason + '\'' +
                ", refundDate=" + refundDate +
                '}';
    }
    
    public static void requestRefund(int id, double amount, String reason)
    {
    	SQLPersistenceHandler.getInstance().requestRefund(id, amount, reason);
    }
}
