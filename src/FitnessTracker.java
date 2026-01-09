import java.util.Scanner;

public class FitnessTracker {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DBHelper.createTable();

        while (true) {
            System.out.println("\n--- FITNESS TRACKER ---");
            System.out.println("1. Add Activity");
            System.out.println("2. View Activities");
            System.out.println("3. Delete Activity");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> DBHelper.addActivity(sc);
                case 2 -> DBHelper.viewActivities();
                case 3 -> DBHelper.deleteActivity(sc);
                case 4 -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
