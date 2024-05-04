package login;

import customer.Customer;

public class Login {
    private String[] usernames;
    private String[] passwords;
    private Customer[] registeredCustomers;
    private int numUsers;

    public Login() {
        usernames = new String[100]; // Adjust the array size as needed
        passwords = new String[100]; // Adjust the array size as needed
        registeredCustomers = new Customer[100]; // Adjust the array size as needed
        numUsers = 0; // Track the number of registered users
    }

    public void addUser(String username, String password, Customer customer) {
        usernames[numUsers] = username;
        passwords[numUsers] = password;
        registeredCustomers[numUsers] = customer;
        numUsers++;
    }

    public Customer isValidUser(String username, String password) {
        for (int i = 0; i < numUsers; i++) {
            if (usernames[i] != null && usernames[i].equals(username) && passwords[i].equals(password)) {
                return registeredCustomers[i];
            }
        }
        return null;
    }
}
