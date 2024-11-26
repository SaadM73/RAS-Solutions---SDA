package Admin;

import Member.*;
import Staff.*;
import application.SQLPersistenceHandler;

import java.sql.Date;

public class FacilityMaintenance {

    private int maintenanceId;
    private int facilityId;
    private Date scheduledDate;
    private String taskDescription;
    private int assignedStaffId;
    private String status;

    // Constructor
    public FacilityMaintenance(int maintenanceId, int facilityId, Date scheduledDate, 
                                String taskDescription, int assignedStaffId, String status) {
        this.maintenanceId = maintenanceId;
        this.facilityId = facilityId;
        this.scheduledDate = scheduledDate;
        this.taskDescription = taskDescription;
        this.assignedStaffId = assignedStaffId;
        this.status = status;
    }

    // Getters and Setters
    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public int getAssignedStaffId() {
        return assignedStaffId;
    }

    public void setAssignedStaffId(int assignedStaffId) {
        this.assignedStaffId = assignedStaffId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ToString Method
    @Override
    public String toString() {
        return "FacilityMaintenance{" +
                "maintenanceId=" + maintenanceId +
                ", facilityId=" + facilityId +
                ", scheduledDate=" + scheduledDate +
                ", taskDescription='" + taskDescription + '\'' +
                ", assignedStaffId=" + assignedStaffId +
                ", status='" + status + '\'' +
                '}';
    }
    
    public static void maintainFacility(FacilityMaintenance fm)
    {
    	SQLPersistenceHandler.getInstance().maintainFacility(fm);
    }
}
