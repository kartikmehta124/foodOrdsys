package mainpackage;
import customer.Customer;
import customer.PremiumCustomer;
import login.Login;
import menu.MenuItem;
import billing.Billing;
import payment.Payment;
import review.Review;

import java.util.Scanner;

class Main {
    private static MenuItem[] menu = new MenuItem[8];  // Adjust the array size as needed

    public static void initializeMenu() {
        menu[0] = new MenuItem("Pizza", 199.00);
        menu[1] = new MenuItem("Burger", 50.00);
        menu[2] = new MenuItem("Cold Coffee", 100.00);
        menu[3] = new MenuItem("Tea", 40.00);
        menu[4] = new MenuItem("Coke", 40.00);
        menu[5] = new MenuItem("Pepsi", 40.00);
        menu[6] = new MenuItem("Orange Juice", 110.00);
        menu[7] = new MenuItem("Apple Juice", 110.00);
    }

    public static void handleMenuSelection(MenuItem[] menu, Billing billing, String username, Customer currentCustomer) {
        int maxItemId = menu.length;
        String[] orderItems = new String[maxItemId];  // Initialize with the maximum possible size
    
        System.out.println("Available Food Items:");
        for (int itemId = 0; itemId < maxItemId; itemId++) {
            MenuItem item = menu[itemId];
            System.out.println((itemId + 1) + ". " + item.getName() + " - " + item.getPrice());
        }
    
        System.out.print("Enter item IDs (comma-separated) for the order: ");
        Scanner scanner = new Scanner(System.in);
        String orderInput = scanner.nextLine();
        String[] orderItemIds = orderInput.split(",");
        int orderItemCount = 0;
    
        for (String inputId : orderItemIds) {
            inputId = inputId.trim();
            if (!inputId.isEmpty()) {
                try {
                    int itemId = Integer.parseInt(inputId);
                    if (itemId >= 1 && itemId <= maxItemId) {
                        orderItems[orderItemCount] = inputId;
                        orderItemCount++;
                    } else {
                        System.out.println("Item with ID " + itemId + " is not available on the menu.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid item ID: " + inputId);
                }
            }
        }
    
        if (orderItemCount == 0) {
            System.out.println("No valid items selected for the order.");
            return;
        }
    
        // Create a new array with the correct size to pass to the billing methods
        String[] finalOrderItems = new String[orderItemCount];
        System.arraycopy(orderItems, 0, finalOrderItems, 0, orderItemCount);
    
        double total = billing.calculateTotal(finalOrderItems, menu);
        System.out.print("Enter Coupon Code: ");
        String couponCode = scanner.nextLine();
        System.out.println();
        billing.generateBill(finalOrderItems, menu, total, username, couponCode, currentCustomer);
        
    
        // Prompt the user for payment method
        System.out.println("Payment Options:");
        System.out.println("1. UPI");
        System.out.println("2. Credit Card");
        System.out.println("3. Debit Card");
        System.out.println();
        System.out.print("Select a payment option (1/2/3): ");
    
        int paymentOption = scanner.nextInt();
        scanner.nextLine();
    
        boolean paymentSuccess = false;
        switch (paymentOption) {
            case 1:
                System.out.print("Enter UPI ID: ");
                String upiId = scanner.nextLine();
                paymentSuccess = Payment.makeUPIPayment(upiId, total);
                break;
            case 2:
                System.out.print("Enter Credit Card Number: ");
                String cardNumber = scanner.nextLine();
                System.out.print("Enter Expiry Date (MM/YY): ");
                String expiryDate = scanner.nextLine();
                System.out.print("Enter CVV: ");
                String cvv = scanner.nextLine();
                System.out.print("Enter OTP: ");
                String otp = scanner.nextLine();
                paymentSuccess = Payment.makeCreditCardPayment(cardNumber, expiryDate, cvv, total, otp);
                break;
            case 3:
                System.out.print("Enter Debit Card Number: ");
                String debitCardNumber = scanner.nextLine();
                System.out.print("Enter Expiry Date (MM/YY): ");
                String debitExpiryDate = scanner.nextLine();
                System.out.print("Enter CVV: ");
                String debitCvv = scanner.nextLine();
                System.out.print("Enter OTP: ");
                String debitOtp = scanner.nextLine();
                paymentSuccess = Payment.makeDebitCardPayment(debitCardNumber, debitExpiryDate, debitCvv, total, debitOtp);
                break;
            default:
                System.out.println("Invalid payment option.");
                break;
        }
    
        if (paymentSuccess) {
            System.out.println();
            System.out.println("Payment successful. Enjoy your meal!");
            System.out.println();
            System.out.println("************************** Thanks For Ordering **************************");
    
        } else {
            System.out.println("Payment failed. Please try again.");
        }
    
        Review.addReviewAndRating(username);
    }
    
    

    public static void main(String[] args) {
        initializeMenu();

        Login loginSystem = new Login();

        // Create a regular customer and a premium customer
        Customer regularCustomer = new Customer("Harshit", "Bapu nagar Bhilwara", "9983937973", "1") {
            public boolean isPremium() {
                return false;
            }
        };

        Customer premiumCustomer = new PremiumCustomer("Keshav", "Kapriwas Haryana", "8575967420", "2");

        loginSystem.addUser("Harshit", "12345", regularCustomer);
        loginSystem.addUser("Keshav", "1245", premiumCustomer);

        System.out.println("********************************************************************");
        System.out.println("*                                                                  *");
        System.out.println("*                    Welcome To Our Page                           *");
        System.out.println("*                                                                  *");
        System.out.println("********************************************************************");

        for (int i = 0; i < 4; i++) {
            System.out.println();
        }

        Scanner scanner = new Scanner(System.in);
        Billing billing = new Billing();

        System.out.println();
        System.out.println("+------------------------------------------------------------------+");
        System.out.println("+                    **************                                +");
        System.out.println("+                     PLEASE LOGIN                                 +");
        System.out.println("+                    **************                                +");
        System.out.println("+------------------------------------------------------------------+");

        for (int i = 0; i < 4; i++) {
            System.out.println();
        }

        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        Customer currentCustomer = null;

        if (loginSystem.isValidUser(username, password) != null) {
            currentCustomer = loginSystem.isValidUser(username, password);

            while (true) {
                System.out.println();
                System.out.println("Hello " + username);
                System.out.println();
                System.out.println("1. Select Menu");
                System.out.println("2. Logout");
                System.out.println("3. Exit");
                System.out.println();
                System.out.print("Enter your choice: ");
                System.out.println();

                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        handleMenuSelection(menu, billing, username, currentCustomer);
                        break;
                    case 2:
                        System.out.println("Logging out...");
                        currentCustomer = null;
                        break;
                    case 3:
                        System.out.println("Exiting Food Ordering System.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                if (currentCustomer == null) {
                    System.out.println("1. Log in again");
                    System.out.println("2. Exit");
                    int loginChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (loginChoice == 1) {
                        System.out.print("Username: ");
                        username = scanner.nextLine();
                        System.out.print("Password: ");
                        password = scanner.nextLine();
                        currentCustomer = loginSystem.isValidUser(username, password);
                    } else if (loginChoice == 2) {
                        System.out.println("Exiting Food Ordering System.");
                        System.exit(0);
                    }
                }
            }
        } else {
            System.out.println("!!!! Please enter correct login details !!!!");
            System.out.println("Press 1 to Sign up");
            System.out.println("Press 2 to exit the system and log in again");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Please Enter your Name: ");
                    String Name = scanner.nextLine();
                    System.out.print("Enter Your Address: ");
                    String Address = scanner.nextLine();
                    System.out.print("Enter Your Contact: ");
                    String Contact = scanner.nextLine();
                    System.out.print("Enter Your Userid: ");
                    String UseriD = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String Password = scanner.nextLine();
                    System.out.println("Press 1 for Regular Customer ");
                    System.out.println("Press 2 for Premium Customer");
                    int type = scanner.nextInt();
                    scanner.nextLine();
                    if (type == 1) {
                        Customer newCustomer = new Customer(Name, Address, Contact, UseriD) {
                            public boolean isPremium() {
                                return false;
                            }
                        };

                        loginSystem.addUser(UseriD, Password, newCustomer);

                        System.out.println("Welcome, " + Name + "! You are now registered.");

                        while (true) {
                            System.out.println();
                            System.out.println("Hello " + username);
                            System.out.println();
                            System.out.println("1. Select Menu");
                            System.out.println("2. Logout");
                            System.out.println("3. Exit");
                            System.out.println();
                            System.out.print("Enter your choice: ");
                            System.out.println();

                            choice = scanner.nextInt();
                            scanner.nextLine();
                            switch (choice) {
                                case 1:
                                    handleMenuSelection(menu, billing, username, newCustomer);
                                    break;
                                case 2:
                                    System.out.println("Logging out...");
                                    newCustomer = null;
                                    break;
                                case 3:
                                    System.out.println("Exiting Food Ordering System.");
                                    System.exit(0);
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                            if (newCustomer == null) {
                                System.out.println("1. Log in again");
                                System.out.println("2. Exit");
                                int loginChoice = scanner.nextInt();
                                scanner.nextLine();
                                if (loginChoice == 1) {
                                    System.out.print("Username: ");
                                    username = scanner.nextLine();
                                    System.out.print("Password: ");
                                    password = scanner.nextLine();
                                    currentCustomer = loginSystem.isValidUser(username, password);
                                } else if (loginChoice == 2) {
                                    System.out.println("Exiting Food Ordering System.");
                                    System.exit(0);
                                }
                            }
                        }
                    } else {
                        PremiumCustomer newCustomer = new PremiumCustomer(Name, Address, Contact, UseriD);
                        loginSystem.addUser(UseriD, Password, newCustomer);

                        System.out.println("Welcome, " + Name + "! You are now registered as a premium customer.");

                        while (true) {
                            System.out.println();
                            System.out.println("Hello " + username);
                            System.out.println();
                            System.out.println("1. Select Menu");
                            System.out.println("2. Logout");
                            System.out.println("3. Exit");
                            System.out.println();
                            System.out.print("Enter your choice: ");
                            System.out.println();

                            choice = scanner.nextInt();
                            scanner.nextLine();
                            switch (choice) {
                                case 1:
                                    handleMenuSelection(menu, billing, username, newCustomer);
                                    break;
                                case 2:
                                    System.out.println("Logging out...");
                                    newCustomer = null;
                                    break;
                                case 3:
                                    System.out.println("Exiting Food Ordering System.");
                                    System.exit(0);
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                            if (newCustomer == null) {
                                System.out.println("1. Log in again");
                                System.out.println("2. Exit");
                                int loginChoice = scanner.nextInt();
                                scanner.nextLine();
                                if (loginChoice == 1) {
                                    System.out.print("Username: ");
                                    username = scanner.nextLine();
                                    System.out.print("Password: ");
                                    password = scanner.nextLine();
                                    currentCustomer = loginSystem.isValidUser(username, password);
                                } else if (loginChoice == 2) {
                                    System.out.println("Exiting Food Ordering System.");
                                    System.exit(0);
                                }
                            }
                        }
                    }
                case 2:
                    System.out.println("Exiting Food Ordering System......");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
