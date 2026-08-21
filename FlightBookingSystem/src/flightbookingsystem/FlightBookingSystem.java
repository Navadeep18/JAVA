package flightbookingsystem;
import java.sql.*;
import java.util.Scanner;

public class FlightBookingSystem {

    static final String URL = "jdbc:mysql://localhost:3306/flightdb";
    static final String USER = "root";
    static final String PASSWORD = "root";

    static Scanner sc = new Scanner(System.in);

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void viewFlights() {

        String sql = "SELECT * FROM flights";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n========== AVAILABLE FLIGHTS ==========");

            while (rs.next()) {
                System.out.println("Flight ID       : " + rs.getInt("flight_id"));
                System.out.println("Flight Name     : " + rs.getString("flight_name"));
                System.out.println("Source          : " + rs.getString("source"));
                System.out.println("Destination     : " + rs.getString("destination"));
                System.out.println("Total Seats     : " + rs.getInt("total_seats"));
                System.out.println("Available Seats : " + rs.getInt("available_seats"));
                System.out.println("---------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void bookTicket() {

        System.out.print("Enter passenger name: ");
        String passengerName = sc.nextLine();

        viewFlights();

        System.out.print("Enter Flight ID: ");
        int flightId = sc.nextInt();

        System.out.print("Enter number of seats: ");
        int seats = sc.nextInt();
        sc.nextLine();

        if (seats <= 0) {
            System.out.println("Invalid number of seats.");
            return;
        }

        Connection con = null;

        try {
            con = getConnection();
            con.setAutoCommit(false);

            String checkSql =
                    "SELECT available_seats FROM flights " +
                    "WHERE flight_id = ? FOR UPDATE";

            PreparedStatement checkStmt = con.prepareStatement(checkSql);
            checkStmt.setInt(1, flightId);

            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Flight not found.");
                con.rollback();
                return;
            }

            int availableSeats = rs.getInt("available_seats");

            if (availableSeats < seats) {
                System.out.println("Only " + availableSeats +
                                   " seats are available.");
                con.rollback();
                return;
            }

            String bookingSql =
                    "INSERT INTO bookings " +
                    "(passenger_name, flight_id, seats_booked) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement bookingStmt =
                    con.prepareStatement(bookingSql);

            bookingStmt.setString(1, passengerName);
            bookingStmt.setInt(2, flightId);
            bookingStmt.setInt(3, seats);

            bookingStmt.executeUpdate();

            String updateSql =
                    "UPDATE flights " +
                    "SET available_seats = available_seats - ? " +
                    "WHERE flight_id = ?";

            PreparedStatement updateStmt =
                    con.prepareStatement(updateSql);

            updateStmt.setInt(1, seats);
            updateStmt.setInt(2, flightId);

            updateStmt.executeUpdate();

            con.commit();

            System.out.println("Ticket booked successfully!");

        } catch (SQLException e) {

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            System.out.println("Booking failed: " + e.getMessage());

        } finally {

            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void viewBookings() {

        String sql =
                "SELECT b.booking_id, b.passenger_name, " +
                "f.flight_name, f.source, f.destination, " +
                "b.seats_booked " +
                "FROM bookings b " +
                "JOIN flights f ON b.flight_id = f.flight_id";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n========== BOOKINGS ==========");

            while (rs.next()) {

                System.out.println("Booking ID      : "
                        + rs.getInt("booking_id"));

                System.out.println("Passenger Name  : "
                        + rs.getString("passenger_name"));

                System.out.println("Flight          : "
                        + rs.getString("flight_name"));

                System.out.println("Route           : "
                        + rs.getString("source") + " -> "
                        + rs.getString("destination"));

                System.out.println("Seats Booked    : "
                        + rs.getInt("seats_booked"));

                System.out.println("--------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void cancelBooking() {

        viewBookings();

        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = sc.nextInt();
        sc.nextLine();

        Connection con = null;

        try {

            con = getConnection();
            con.setAutoCommit(false);

            String selectSql =
                    "SELECT flight_id, seats_booked " +
                    "FROM bookings " +
                    "WHERE booking_id = ? FOR UPDATE";

            PreparedStatement selectStmt =
                    con.prepareStatement(selectSql);

            selectStmt.setInt(1, bookingId);

            ResultSet rs = selectStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Booking not found.");
                con.rollback();
                return;
            }

            int flightId = rs.getInt("flight_id");
            int seatsBooked = rs.getInt("seats_booked");

            String deleteSql =
                    "DELETE FROM bookings WHERE booking_id = ?";

            PreparedStatement deleteStmt =
                    con.prepareStatement(deleteSql);

            deleteStmt.setInt(1, bookingId);
            deleteStmt.executeUpdate();

            String updateSql =
                    "UPDATE flights " +
                    "SET available_seats = available_seats + ? " +
                    "WHERE flight_id = ?";

            PreparedStatement updateStmt =
                    con.prepareStatement(updateSql);

            updateStmt.setInt(1, seatsBooked);
            updateStmt.setInt(2, flightId);

            updateStmt.executeUpdate();

            con.commit();

            System.out.println("Booking cancelled successfully!");
            System.out.println(seatsBooked +
                    " seat(s) have been made available again.");

        } catch (SQLException e) {

            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            System.out.println("Cancellation failed: "
                    + e.getMessage());

        } finally {

            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver Loaded Successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            return;
        }

        while (true) {

            System.out.println("\n========== FLIGHT BOOKING SYSTEM ==========");
            System.out.println("1. View Available Flights");
            System.out.println("2. Book Ticket");
            System.out.println("3. View Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    viewFlights();
                    break;

                case 2:
                    bookTicket();
                    break;

                case 3:
                    viewBookings();
                    break;

                case 4:
                    cancelBooking();
                    break;

                case 5:
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}