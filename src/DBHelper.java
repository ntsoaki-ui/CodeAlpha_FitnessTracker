import java.sql.*;
import java.util.Scanner;

public class DBHelper {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found!");
        }
    }

    private static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:fitness.db");
        } catch (SQLException e) {
            return null;
        }
    }

    public static void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS activities (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    type TEXT,
                    duration INTEGER,
                    calories INTEGER
                );
                """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception ignored) {
        }
    }

    public static void addActivity(Scanner sc) {
        System.out.print("Activity type: ");
        String type = sc.next();

        System.out.print("Duration (minutes): ");
        int duration = sc.nextInt();

        System.out.print("Calories burned: ");
        int calories = sc.nextInt();

        String sql = "INSERT INTO activities VALUES (NULL, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, type);
            ps.setInt(2, duration);
            ps.setInt(3, calories);
            ps.executeUpdate();
            System.out.println("Activity added");

        } catch (Exception e) {
            System.out.println("Error adding activity");
        }
    }

    public static void viewActivities() {
        String sql = "SELECT * FROM activities";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nID | Type | Duration | Calories");
            while (rs.next()) {
                System.out.println(
                        rs.getInt(1) + " | " +
                                rs.getString(2) + " | " +
                                rs.getInt(3) + " | " +
                                rs.getInt(4)
                );
            }
        } catch (Exception e) {
            System.out.println("Error reading data");
        }
    }

    public static void deleteActivity(Scanner sc) {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM activities WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Activity deleted");

        } catch (Exception e) {
            System.out.println("Delete failed");
        }
    }
}
