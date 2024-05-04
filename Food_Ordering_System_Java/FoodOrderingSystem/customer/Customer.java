

package customer;

public abstract class Customer {
    private String name;
    private String address;
    private String contact;
    private String userId;
    
    

    public Customer(String name, String address, String contact, String userId) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }
    public abstract boolean isPremium();
}

