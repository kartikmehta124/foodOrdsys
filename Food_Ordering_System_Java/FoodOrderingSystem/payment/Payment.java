package payment;

public class Payment {
    public static boolean makeUPIPayment(String upiId, double amount) {
        System.out.println("Making UPI payment to " + upiId + " for amount: " + amount);
        return true; 
    }
    public static boolean makeCreditCardPayment(String cardNumber, String expiryDate, String cvv, double amount, String otp) {
        if (validateOTP(otp)) {
            System.out.println("Making credit card payment with card number " + cardNumber + " for amount: " + amount);
           
            return true; 
        } else {
            System.out.println("Invalid OTP. Payment failed.");
            return false;
        }
    }

    // Debit Card Payment Method with OTP Verification
    public static boolean makeDebitCardPayment(String cardNumber, String expiryDate, String cvv, double amount, String otp) {
        if (validateOTP(otp)) {
            System.out.println("Making debit card payment with card number " + cardNumber + " for amount: " + amount);
            return true; 
        } else {
            System.out.println("Invalid OTP. Payment failed.");
            return false;
        }
    }

    private static boolean validateOTP(String otp) {
        return otp.equals("123456");
    }
}