#conference-room:


class ConferenceRoom {
    private final String roomId;
    private final TreeMap<Integer, Integer> bookings; // Stores slots booked

    public ConferenceRoom(String roomId) {
        this.roomId = roomId;
        this.bookings = new TreeMap<>();
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isAvailable(int start, int end) {
        // Check for any overlapping bookings
        for (Map.Entry<Integer, Integer> entry : bookings.entrySet()) {
            if (Math.max(entry.getKey(), start) < Math.min(entry.getValue(), end)) {
                return false;
            }
        }
        return true;
    }

    public void bookSlot(int start, int end) {
        bookings.put(start, end);
    }

    public void cancelSlot(int start, int end) {
        bookings.remove(start, end);
    }

    public Map<Integer, Integer> getBookings() {
        return bookings;
    }
}

#floor:
class Floor {
    private final String floorName;
    private final Map<String, ConferenceRoom> conferenceRooms;

    public Floor(String floorName) {
        this.floorName = floorName;
        this.conferenceRooms = new HashMap<>();
    }

    public void addConferenceRoom(ConferenceRoom room) {
        conferenceRooms.put(room.getRoomId(), room);
    }

    public ConferenceRoom getConferenceRoom(String roomId) {
        return conferenceRooms.get(roomId);
    }

    public Collection<ConferenceRoom> getAllConferenceRooms() {
        return conferenceRooms.values();
    }
}
#building: 

class Building {
    private final String buildingName;
    private final Map<String, Floor> floors;

    public Building(String buildingName) {
        this.buildingName = buildingName;
        this.floors = new HashMap<>();
    }

    public void addFloor(Floor floor) {
        floors.put(floor.floorName, floor);
    }

    public Floor getFloor(String floorName) {
        return floors.get(floorName);
    }

    public Collection<Floor> getAllFloors() {
        return floors.values();
    }
}


#booking service 


class BookingService {
    private static BookingService instance;
    private final Map<String, Building> buildings;

    private BookingService() {
        buildings = new HashMap<>();
    }

    public static BookingService getInstance() {
        if (instance == null) {
            instance = new BookingService();
        }
        return instance;
    }

    public void addBuilding(String buildingName) {
        buildings.put(buildingName, new Building(buildingName));
        System.out.println("Added building " + buildingName + " into the system.");
    }

    public void addFloor(String buildingName, String floorName) {
        Building building = buildings.get(buildingName);
        if (building != null) {
            building.addFloor(new Floor(floorName));
            System.out.println("Added floor " + floorName + " in building " + buildingName);
        }
    }

    public void addConferenceRoom(String buildingName, String floorName, String roomId) {
        Building building = buildings.get(buildingName);
        if (building != null) {
            Floor floor = building.getFloor(floorName);
            if (floor != null) {
                floor.addConferenceRoom(new ConferenceRoom(roomId));
                System.out.println("Added conference room " + roomId + " on " + floorName + " in " + buildingName);
            }
        }
    }

    public boolean bookRoom(String buildingName, String floorName, String roomId, int start, int end) {
        Floor floor = buildings.get(buildingName).getFloor(floorName);
        ConferenceRoom room = floor.getConferenceRoom(roomId);
        if (room != null && room.isAvailable(start, end)) {
            room.bookSlot(start, end);
            System.out.println("Room booked successfully: " + roomId + " from " + start + " to " + end);
            return true;
        }
        System.out.println("Room not available for the given slot.");
        return false;
    }

    public void cancelBooking(String buildingName, String floorName, String roomId, int start, int end) {
        ConferenceRoom room = buildings.get(buildingName).getFloor(floorName).getConferenceRoom(roomId);
        if (room != null) {
            room.cancelSlot(start, end);
            System.out.println("Cancelled booking for room " + roomId + " from " + start + " to " + end);
        }
    }

    public void listBookings(String buildingName, String floorName) {
        Floor floor = buildings.get(buildingName).getFloor(floorName);
        if (floor != null) {
            for (ConferenceRoom room : floor.getAllConferenceRooms()) {
                for (Map.Entry<Integer, Integer> entry : room.getBookings().entrySet()) {
                    System.out.println(entry.getKey() + ":" + entry.getValue() + " " + floorName + " " + buildingName + " " + room.getRoomId());
                }
            }
        }
    }

    public List<String> searchAvailableRooms(String buildingName, String floorName, int start, int end) {
        Floor floor = buildings.get(buildingName).getFloor(floorName);
        List<String> availableRooms = new ArrayList<>();
        for (ConferenceRoom room : floor.getAllConferenceRooms()) {
            if (room.isAvailable(start, end)) {
                availableRooms.add(room.getRoomId());
            }
        }
        return availableRooms;
    }
}




driver : 


public class Driver {
    public static void main(String[] args) {
        BookingService service = BookingService.getInstance();

        service.addBuilding("Flipkart1");
        service.addFloor("Flipkart1", "FirstFloor");
        service.addConferenceRoom("Flipkart1", "FirstFloor", "C1");
        service.addConferenceRoom("Flipkart1", "FirstFloor", "C2");

        // Booking a room
        service.bookRoom("Flipkart1", "FirstFloor", "C1", 1, 5);

        // Trying to book the same slot in the same room
        service.bookRoom("Flipkart1", "FirstFloor", "C1", 3, 6);

        // Searching for available rooms
        List<String> availableRooms = service.searchAvailableRooms("Flipkart1", "FirstFloor", 2, 3);
        System.out.println("Available Rooms: " + availableRooms);

        // Listing all bookings
        service.listBookings("Flipkart1", "FirstFloor");

        // Canceling a booking
        service.cancelBooking("Flipkart1", "FirstFloor", "C1", 1, 5);

        // Listing all bookings after cancellation
        service.listBookings("Flipkart1", "FirstFloor");
    }
}




