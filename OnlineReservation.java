import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OnlineReservation {
    private Map<String, String> users; // For storing username and passwords
    private Map<String, Map<String, String>> reservations; // For storing reservation data
    
    public OnlineReservation() {
        users = new HashMap<>();
        reservations = new HashMap<>();
    }
    
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    register(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
            
            System.out.println();
        }
    }
    
    private void login(Scanner scanner) {
        System.out.println();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        if (users.containsKey(username) && users.get(username).equals(password)) {
            System.out.println();
            System.out.println("Logged in successfully...");
            System.out.println();
            reservationMenu(scanner, username);
        } else {
            System.out.println();
            System.out.println("Invalid username or password...");
        }
    }
    
    private void register(Scanner scanner) {
        System.out.println();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        if (users.containsKey(username)) {
            System.out.println();
            System.out.println("Username already exists. Try again.");
            return;
        }
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        users.put(username, password);
        System.out.println();
        System.out.println("Registration successful... \nYou can now log in to the system...");
    }
    
    private void reservationMenu(Scanner scanner, String username) {
        while (true) {
            System.out.println("1. Make a reservation");
            System.out.println("2. Cancel a reservation");
            System.out.println("3. Show reservations");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    makeReservation(scanner, username);
                    break;
                case 2:
                    cancelReservation(scanner, username);
                    break;
                case 3:
                    showReservations(username);
                    break;
                case 4:
                    System.out.println();
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
            
            System.out.println();
        }
    }
    
    private void makeReservation(Scanner scanner, String username) {
        System.out.println();
        System.out.print("Enter reservation details (train name, name, origin, destination, PNR): ");
        String reservationDetails = scanner.nextLine();
        
        // Split the input by comma to extract individual details
        String[] details = reservationDetails.split(",");
        
        // Check if all details are provided
        if (details.length != 5) {
            System.out.println("Invalid input format. Please provide train name, name, origin, destination, and PNR number separated by commas.");
            return;
        }
        
        // Extract individual details
        String trainName = details[0].trim();
        String name = details[1].trim();
        String origin = details[2].trim();
        String destination = details[3].trim();
        String pnr = details[4].trim();
        
        // Check if user already has reservations
        if (!reservations.containsKey(username)) {
            reservations.put(username, new HashMap<>());
        }
        
        // Check if the given PNR number already exists for the user
        if (reservations.get(username).containsKey(pnr)) {
            System.out.println("Reservation with this PNR already exists. Please provide a different PNR number.");
            return;
        }
        
        // Create a formatted reservation string
        String formattedReservation = "Train Name: " + trainName + ", Name: " + name + ", Origin: " + origin + ", Destination: " + destination + ", PNR: " + pnr;
        
        // Add the reservation to the user's list of reservations
        reservations.get(username).put(pnr, formattedReservation);
        
        System.out.println("Reservation created successfully...");
    }
    
    private void cancelReservation(Scanner scanner, String username) {
        System.out.println();
        System.out.print("Enter PNR number to cancel reservation: ");
        String pnrToCancel = scanner.nextLine();
        
        if (reservations.containsKey(username) && reservations.get(username).containsKey(pnrToCancel)) {
            // Cancel the reservation with the given PNR number
            reservations.get(username).remove(pnrToCancel);
            System.out.println("Reservation with PNR " + pnrToCancel + " canceled successfully...");
        } else {
            System.out.println("No reservation found with the given PNR number.");
        }
    }
    
    private void showReservations(String username) {
        if (reservations.containsKey(username)) {
            System.out.println("Your Reservations:");
            Map<String, String> userReservations = reservations.get(username);
            for (Map.Entry<String, String> entry : userReservations.entrySet()) {
                System.out.println("PNR: " + entry.getKey() + ", Reservation: " + entry.getValue());
            }
        } else {
            System.out.println("You don't have any reservations.");
        }
    }
    
    public static void main(String[] args) {
        OnlineReservation reservationSystem = new OnlineReservation();
        reservationSystem.execute();
    }
}