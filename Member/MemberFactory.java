package Member;

public class MemberFactory {

    public static Member createMember(int memberId, String name, String email, String password, String phoneNumber, String membershipType) {
        switch (membershipType.toLowerCase()) {
            case "basic":
                return new BasicMember(memberId, name, email, password, phoneNumber, membershipType);
            case "premium":
                return new PremiumMember(memberId, name, email, password, phoneNumber, membershipType);
            case "vip":
                return new VipMember(memberId, name, email, password, phoneNumber, membershipType);               
            default:
                throw new IllegalArgumentException("Unknown membership type: " + membershipType);
        }
    }
}
