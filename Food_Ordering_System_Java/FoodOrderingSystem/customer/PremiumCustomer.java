package customer;

public class PremiumCustomer extends Customer {
    public PremiumCustomer(String name, String address, String contact, String userId) {
        super(name, address, contact, userId);
    }

   
    public boolean isPremium() {
        return true; 
    }
}
