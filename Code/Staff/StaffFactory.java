package Staff;

public class StaffFactory 
{
	public static Staff createStaff(int staffId, String name, String role, String email, String phoneNumber, String password) {
        switch (role.toLowerCase()) {
            case "receptionist":
                return new ReceptionistStaff(staffId, name, role, email, phoneNumber, password);
            case "trainer":
                return new TrainerStaff(staffId, name, role, email, phoneNumber, password);
            case "manager":
                return new ManagerStaff(staffId, name, role, email, phoneNumber, password);   
            case "maintenance staff":
            return new MaintainenanceStaff(staffId, name, role, email, phoneNumber, password);   
            
            default:
                throw new IllegalArgumentException("Unknown role type: " + role);
        }
    }
}
