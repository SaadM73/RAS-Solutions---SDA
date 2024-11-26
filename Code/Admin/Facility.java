package Admin;

import Member.*;
import Staff.*;
import application.SQLPersistenceHandler;

import java.sql.SQLException;
import java.util.List;
public class Facility {
    private int facilityId;
    private String name;
    private String type;
    private String availabilityStatus;
    private int capacity;

    // Constructor
    public Facility(int facilityId, String name, String type, String availabilityStatus, int capacity) {
        this.facilityId = facilityId;
        this.name = name;
        this.type = type;
        this.availabilityStatus = availabilityStatus;
        this.capacity = capacity;
    }

    // Getters
    public int getFacilityId() {
        return facilityId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public int getCapacity() {
        return capacity;
    }

    // Setters
    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // toString Method for debugging
    @Override
    public String toString() {
        return "Facility{" +
                "facilityId=" + facilityId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", availabilityStatus='" + availabilityStatus + '\'' +
                ", capacity=" + capacity +
                '}';
    }
    
    public static void insertFacility(Facility facility)
    {
    	SQLPersistenceHandler.getInstance().insertFacility(facility);
    }
    
    public static int FacilityNametoID(String name)
    {
    	return SQLPersistenceHandler.getInstance().FacilityNametoID(name);
    }
    
    public static boolean isValidFacilityId(int id)
    {
    	return SQLPersistenceHandler.getInstance().isValidFacilityId(id);
    }
    
    public static List <Facility> getAllFacilities() throws SQLException
    {
    	return SQLPersistenceHandler.getInstance().getAllFacilities();
    }
    
    public static Facility getFacilityById(int id) throws SQLException
    {
    	return SQLPersistenceHandler.getInstance().getFacilityById(id);
    }
    
    public static void deleteFacility(int id)
    {
    	SQLPersistenceHandler.getInstance().deleteFacility(id);
    }
    
    public static List<Facility> getFacilitiesOrderedByAvailability()
    {
    	return SQLPersistenceHandler.getInstance().getFacilitiesOrderedByAvailability();
    }
}
