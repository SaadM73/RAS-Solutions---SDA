package Member;
import Staff.*;
import application.SQLPersistenceHandler;
import Admin.*;
import java.time.LocalDate;
import java.util.List;

public class Membership {
    private int membershipId;
    private int memberId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public Membership(int membershipId, int memberId, LocalDate startDate, LocalDate endDate, String status) {
        this.membershipId = membershipId;
        this.memberId = memberId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getters and Setters
    public int getMembershipId()
    {
        return membershipId;
    }

    public void setMembershipId(int membershipId)
    {
        this.membershipId = membershipId;
    }

    public int getMemberId()
    {
        return memberId;
    }

    public void setMemberId(int memberId)
    {
        this.memberId = memberId;
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) 
    {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() 
    {
        return endDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }
    
    public static void insertMembership(int id)
    {
    	SQLPersistenceHandler.getInstance().insertMembership(id);
    }
    
    public static Membership getMembershipDetails(int id)
    {
    	return SQLPersistenceHandler.getInstance().getMembershipDetails(id);
    }
    
    public static List<Membership> getAllMemberships()
    {
    	return SQLPersistenceHandler.getInstance().getAllMemberships();
    }
    
    public static boolean cancelMembership(int id)
    {
    	return SQLPersistenceHandler.getInstance().cancelMembership(id);
    }
    
    public static List <Membership> getMembershipsByMemberId(int id)
    {
    	return SQLPersistenceHandler.getInstance().getMembershipsByMemberId(id);
    }
}
