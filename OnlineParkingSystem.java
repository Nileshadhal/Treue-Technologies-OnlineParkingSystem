import java.util.*;
import java.time.Duration;
import java.time.LocalDateTime;

class ParkingSpot {
    private int spotId;
    private boolean isBooked;

    public ParkingSpot(int spotId) {
        this.spotId = spotId;
        this.isBooked = false;
    }

    public int getSpotId() {
        return spotId;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void book() {
        isBooked = true;
    }

    public void unbook() {
        isBooked = false;
    }
}

class User {
    private String username;
    private String password;
    private List<ParkingSpot> bookings;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.bookings = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<ParkingSpot> getBookings() {
        return bookings;
    }
}

public class OnlineParkingSystem {
    private List<ParkingSpot> parkingSpots;
    private List<User> users;
    private Scanner scanner;

    public OnlineParkingSystem() {
        parkingSpots = new ArrayList<>();
        users = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void registerUser() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();

        
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists. Please choose a different one.");
                return;
            }
        }

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        User newUser = new User(username, password);
        users.add(newUser);
        System.out.println("User registered successfully.");
    }

	private User currentUser;

    public void login() {
        if (currentUser != null) {
            System.out.println("You are already logged in as " + currentUser.getUsername());
            return;
        }

        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Enter your password:");
                String password = scanner.nextLine();
                if (user.getPassword().equals(password)) {
                    currentUser = user;
                    System.out.println("Login successful. Welcome, " + currentUser.getUsername() + "!");
                } else {
                    System.out.println("Incorrect password. Login failed.");
                }
                return;
            }
        }

        System.out.println("User not found. Please register or try again.");
    }

    public void displayAvailableParkingSpots() {
        System.out.println("Available parking spots:");
        for (ParkingSpot spot : parkingSpots) {
            if (!spot.isBooked()) {
                System.out.println("Spot ID: " + spot.getSpotId());
            }
        }
    }

    public void bookParkingSpot(User user) {
    System.out.println("Enter the spot ID you want to book:");
    int spotId = scanner.nextInt();
    scanner.nextLine();

    ParkingSpot selectedSpot = null;
    for (ParkingSpot spot : parkingSpots) {
        if (spot.getSpotId() == spotId && !spot.isBooked()) {
            selectedSpot = spot;
            break;
        }
    }

    if (selectedSpot == null) {
        System.out.println("Invalid spot ID or the spot is already booked.");
    } else {
        System.out.println("Enter booking duration in minutes:");
        int bookingDuration = scanner.nextInt();
        scanner.nextLine();

        LocalDateTime endTime = LocalDateTime.now().plusMinutes(bookingDuration);
        selectedSpot.book();
        user.getBookings().add(selectedSpot);
        System.out.println("Booking confirmed. Enjoy your parking until " + endTime);
    }
}
	public void logout() {
        if (currentUser == null) {
            System.out.println("You are not logged in.");
            return;
        }

        currentUser = null;
        System.out.println("Logout successful. Goodbye!");
    }
//new
	public void viewUserBookings(User user) {
        List<ParkingSpot> bookings = user.getBookings();
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings.");
        } else {
            System.out.println("Your bookings:");
            for (ParkingSpot spot : bookings) {
                System.out.println("Spot ID: " + spot.getSpotId());
            }
        }
    }

    public void cancelBooking(User user) {
        List<ParkingSpot> bookings = user.getBookings();
        if (bookings.isEmpty()) {
            System.out.println("You have no bookings to cancel.");
            return;
        }

        System.out.println("Your bookings:");
        for (ParkingSpot spot : bookings) {
            System.out.println("Spot ID: " + spot.getSpotId());
        }

        System.out.println("Enter the spot ID you want to cancel:");
        int spotId = scanner.nextInt();
        scanner.nextLine();

        boolean found = false;
        for (ParkingSpot spot : bookings) {
            if (spot.getSpotId() == spotId) {
                spot.unbook();
                bookings.remove(spot);
                System.out.println("Booking canceled successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Spot ID not found in your bookings.");
        }
    }

    public static void main(String[] args) {
        OnlineParkingSystem parkingSystem = new OnlineParkingSystem();
        parkingSystem.parkingSpots.add(new ParkingSpot(1));
        parkingSystem.parkingSpots.add(new ParkingSpot(2));
        parkingSystem.parkingSpots.add(new ParkingSpot(3));

   
        while (true) {
        System.out.println("\n======= Online Parking System =======");
        System.out.println("1. User Register");
        System.out.println("2. Login");
        System.out.println("3. Display Available Parking Spots");
        System.out.println("4. Book Parking Spot");
        System.out.println("5. View User Bookings ");
        System.out.println("6. Cancel Booking");
		System.out.println("7. Logout");
        System.out.println("8. Exit");
		System.out.println(" ");
        System.out.println("* Enter your choice: ");
	
            int choice = parkingSystem.scanner.nextInt();
            parkingSystem.scanner.nextLine(); 

            switch (choice) {
                case 1:
                    parkingSystem.registerUser();
                    break;
				case 2:
					parkingSystem.login();
					break;
                case 3:
                    parkingSystem.displayAvailableParkingSpots();
                    break;
                case 4:
					System.out.print("Enter your Vehicle No :");
                    String us = parkingSystem.scanner.nextLine();
                    System.out.print("Enter your name:");
                    String username = parkingSystem.scanner.nextLine();
                    User currentUser = null;
                    for (User user : parkingSystem.users) {
                        if (user.getUsername().equals(username)) {
                            currentUser = user;
                            break;
                        }
                    }
                    if (currentUser != null) {
                        parkingSystem.bookParkingSpot(currentUser);
                    } else {
                        System.out.println("User not found. Please Login first.");
                    }
                    break;
                
				case 5:
                    if (parkingSystem.currentUser != null) {
                        parkingSystem.viewUserBookings(parkingSystem.currentUser);
                    } else {
                        System.out.println("You need to login first.");
                    }
                    break;
                case 6:
                    if (parkingSystem.currentUser != null) {
                        parkingSystem.cancelBooking(parkingSystem.currentUser);
                    } else {
                        System.out.println("You need to login first.");
                    } 
					break;
				case 7:
					parkingSystem.logout();
					break;

				case 8:
                    System.out.println("Goodbye!");
                    System.exit(0);

           
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
