package Admin;

public class FacilityFactory {

    public static Facility createFacility( int facilityId, String name, String type, String availabilityStatus, int capacity) {
        switch (type.toLowerCase()) {
            case "sports":
                return new SportsFacility(facilityId, name,  availabilityStatus, capacity);
            case "recreational":
                return new RecreationalFacility(facilityId, name, availabilityStatus, capacity);
            case "fitness":
                return new FitnessFacility(facilityId, name, availabilityStatus, capacity);
            case "meeting":
                return new MeetingFacility(facilityId, name, availabilityStatus, capacity);
            default:
                throw new IllegalArgumentException("Unknown facility type: " + type);
        }
    }
}
