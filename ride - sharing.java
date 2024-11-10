# ride - sharing : 


// user class 
import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String gender;
    private int age;
    private List<Vehicle> vehicles;
    private int ridesTaken;
    private int ridesOffered;

    public User(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.vehicles = new ArrayList<>();
        this.ridesTaken = 0;
        this.ridesOffered = 0;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void incrementRidesTaken() { ridesTaken++; }
    public void incrementRidesOffered() { ridesOffered++; }

    public String getName() { return name; }
    public int getRidesTaken() { return ridesTaken; }
    public int getRidesOffered() { return ridesOffered; }
}

// vehicle class 


public class Vehicle {
    private String vehicleType;
    private String vehicleNumber;

    public Vehicle(String vehicleType, String vehicleNumber) {
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleType() { return vehicleType; }
    public String getVehicleNumber() { return vehicleNumber; }
}


// ride class 

public class Ride {
    private User driver;
    private Vehicle vehicle;
    private String origin;
    private String destination;
    private int availableSeats;
    private boolean isActive;

    public Ride(User driver, Vehicle vehicle, String origin, String destination, int availableSeats) {
        this.driver = driver;
        this.vehicle = vehicle;
        this.origin = origin;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.isActive = true;
    }

    public void endRide() { this.isActive = false; }

    public User getDriver() { return driver; }
    public Vehicle getVehicle() { return vehicle; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public int getAvailableSeats() { return availableSeats; }
    public boolean isActive() { return isActive; }
}


// ride selection strategy 

public interface RideSelectionStrategy {
    Ride selectRide(List<Ride> rides, String preferredVehicleType);
}


// most vacant selection strategy 

import java.util.List;

public class MostVacantSelectionStrategy implements RideSelectionStrategy {
    @Override
    public Ride selectRide(List<Ride> rides, String preferredVehicleType) {
        return rides.stream()
                .filter(Ride::isActive)
                .max((r1, r2) -> Integer.compare(r1.getAvailableSeats(), r2.getAvailableSeats()))
                .orElse(null);
    }
}



// preferred vehicle selection strategy 

import java.util.List;

public class PreferredVehicleSelectionStrategy implements RideSelectionStrategy {
    @Override
    public Ride selectRide(List<Ride> rides, String preferredVehicleType) {
        return rides.stream()
                .filter(ride -> ride.isActive() && ride.getVehicle().getVehicleType().equalsIgnoreCase(preferredVehicleType))
                .findFirst()
                .orElse(null);
    }
}



// ride service single ton 

import java.util.*;

public class RideService {
    private static RideService instance;
    private Map<String, User> users;
    private List<Ride> rides;

    private RideService() {
        users = new HashMap<>();
        rides = new ArrayList<>();
    }

    public static RideService getInstance() {
        if (instance == null) {
            instance = new RideService();
        }
        return instance;
    }

    public void addUser(String name, String gender, int age) {
        users.put(name, new User(name, gender, age));
    }

    public void addVehicle(String userName, String vehicleType, String vehicleNumber) {
        User user = users.get(userName);
        if (user != null) {
            user.addVehicle(new Vehicle(vehicleType, vehicleNumber));
        }
    }

    public void offerRide(String userName, String vehicleNumber, String origin, String destination, int seats) {
        User user = users.get(userName);
        if (user == null) return;
        Vehicle vehicle = user.getVehicles().stream()
                .filter(v -> v.getVehicleNumber().equals(vehicleNumber))
                .findFirst().orElse(null);

        if (vehicle != null) {
            Ride ride = new Ride(user, vehicle, origin, destination, seats);
            rides.add(ride);
            user.incrementRidesOffered();
        }
    }

    public Ride selectRide(String origin, String destination, int seats, RideSelectionStrategy strategy, String preferredVehicleType) {
        List<Ride> matchingRides = rides.stream()
                .filter(ride -> ride.isActive() && ride.getOrigin().equalsIgnoreCase(origin) && ride.getDestination().equalsIgnoreCase(destination) && ride.getAvailableSeats() >= seats)
                .toList();
        return strategy.selectRide(matchingRides, preferredVehicleType);
    }

    public void endRide(Ride ride) {
        ride.endRide();
    }

    public void printRideStats() {
        for (User user : users.values()) {
            System.out.println(user.getName() + ": " + user.getRidesTaken() + " Taken, " + user.getRidesOffered() + " Offered");
        }
    }
}



// driver class 


public class Driver {
    public static void main(String[] args) {
        RideService service = RideService.getInstance();

        // Onboard users
        service.addUser("Rohan", "M", 36);
        service.addVehicle("Rohan", "Swift", "KA-01-12345");

        service.addUser("Shipra", "F", 27);
        service.addVehicle("Shipra", "Polo", "KA-05-41491");
        service.addVehicle("Shipra", "Activa", "KA-12-12332");

        service.addUser("Shashank", "M", 29);
        service.addVehicle("Shashank", "Baleno", "TS-05-62395");

        // Offer rides
        service.offerRide("Rohan", "KA-01-12345", "Hyderabad", "Bangalore", 1);
        service.offerRide("Shipra", "KA-05-41491", "Bangalore", "Mysore", 2);

        // Select a ride using different strategies
        Ride ride1 = service.selectRide("Bangalore", "Mysore", 1, new MostVacantSelectionStrategy(), null);
        if (ride1 != null) {
            System.out.println("Selected ride: " + ride1.getDriver().getName() + " with vehicle " + ride1.getVehicle().getVehicleType());
        }

        service.printRideStats();
    }
}





