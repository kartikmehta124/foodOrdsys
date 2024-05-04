package review;

import java.util.Scanner;

public class Review {
    private static String[] userIds = new String[100];  // Adjust the array size as needed
    private static String[] reviews = new String[100];  // Adjust the array size as needed
    private static float[] ratings = new float[100];    // Adjust the array size as needed
    private static int reviewCount = 0;


    public static void addReviewAndRating(String username) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please give the review: ");
        String userReview = scanner.nextLine();
        System.out.print("Rating (1-5): ");
        float userRating = scanner.nextFloat();

        // Validate rating (between 1 and 5)
        if (userRating < 1 || userRating > 5) {
            System.out.println("Invalid rating. Please enter a number between 1 and 5.");
        } else {
            // Store the review and rating in arrays
            userIds[reviewCount] = username;
            reviews[reviewCount] = userReview;
            ratings[reviewCount] = userRating;
            reviewCount++;
            System.out.println("Thank you for your review and rating!");
        }
    }
}
