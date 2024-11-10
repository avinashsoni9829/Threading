class MenuItem {
    String name;
    int price;

    public MenuItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + ": " + price;
    }
}




class Restaurant {
    String name;
    Map<String, MenuItem> menu;
    int totalCapacity;
    AtomicInteger capacityInUse;

    public Restaurant(String name, Map<String, MenuItem> menu, int capacity) {
        this.name = name;
        this.menu = menu;
        this.totalCapacity = capacity;
        this.capacityInUse = new AtomicInteger(0);
    }

    public synchronized boolean canProcessOrder(int itemCount) {
        return capacityInUse.get() + itemCount <= totalCapacity;
    }

    public synchronized void addOrder(int itemCount) {
        capacityInUse.addAndGet(itemCount);
    }

    public synchronized void completeOrder(int itemCount) {
        capacityInUse.addAndGet(-itemCount);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", menu=" + menu +
                ", totalCapacity=" + totalCapacity +
                ", capacityInUse=" + capacityInUse +
                '}';
    }
}




class Order {
    static int orderCounter = 1;
    String orderId;
    String customerName;
    Map<String, Integer> items;
    int totalCost;
    Restaurant restaurant;

    public Order(String customerName, Map<String, Integer> items, Restaurant restaurant, int totalCost) {
        this.orderId = "order" + orderCounter++;
        this.customerName = customerName;
        this.items = items;
        this.restaurant = restaurant;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", items=" + items +
                ", totalCost=" + totalCost +
                ", restaurant=" + restaurant.name +
                '}';
    }
}



class RestaurantService {
    private final Map<String, Restaurant> restaurants = new HashMap<>();

    public void addRestaurant(String name, Map<String, Integer> menuItems, int capacity) {
        Map<String, MenuItem> menu = new HashMap<>();
        for (Map.Entry<String, Integer> entry : menuItems.entrySet()) {
            menu.put(entry.getKey(), new MenuItem(entry.getKey(), entry.getValue()));
        }
        restaurants.put(name, new Restaurant(name, menu, capacity));
        System.out.println("Added restaurant: " + name);
    }

    public void updateMenu(String restaurantName, Map<String, Integer> menuItems) {
        Restaurant restaurant = restaurants.get(restaurantName);
        if (restaurant != null) {
            for (Map.Entry<String, Integer> entry : menuItems.entrySet()) {
                restaurant.menu.put(entry.getKey(), new MenuItem(entry.getKey(), entry.getValue()));
            }
            System.out.println("Updated menu for restaurant: " + restaurantName);
        }
    }

    public Restaurant findBestRestaurant(Map<String, Integer> items) {
        Restaurant selectedRestaurant = null;
        int minCost = Integer.MAX_VALUE;

        for (Restaurant restaurant : restaurants.values()) {
            int cost = calculateCost(restaurant, items);
            if (cost != -1 && cost < minCost && restaurant.canProcessOrder(items.size())) {
                minCost = cost;
                selectedRestaurant = restaurant;
            }
        }
        return selectedRestaurant;
    }

    private int calculateCost(Restaurant restaurant, Map<String, Integer> items) {
        int totalCost = 0;
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            MenuItem menuItem = restaurant.menu.get(entry.getKey());
            if (menuItem == null) return -1;
            totalCost += menuItem.price * entry.getValue();
        }
        return totalCost;
    }

    public List<Restaurant> listRestaurants() {
        return new ArrayList<>(restaurants.values());
    }
}



class OrderService {
    private final List<Order> orders = new ArrayList<>();
    private final RestaurantService restaurantService;

    public OrderService(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public void placeOrder(String customerName, Map<String, Integer> items) {
        Restaurant restaurant = restaurantService.findBestRestaurant(items);
        if (restaurant == null) {
            System.out.println("No restaurant can fulfill the order");
            return;
        }
        int totalCost = 0;
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            totalCost += restaurant.menu.get(entry.getKey()).price * entry.getValue();
        }
        restaurant.addOrder(items.size());
        Order order = new Order(customerName, items, restaurant, totalCost);
        orders.add(order);
        System.out.println("Order placed: " + order);
    }

    public void markAsDelivered(String orderId) {
        for (Order order : orders) {
            if (order.orderId.equals(orderId)) {
                order.restaurant.completeOrder(order.items.size());
                System.out.println("Order delivered: " + orderId);
                return;
            }
        }
        System.out.println("Order not found: " + orderId);
    }

    public List<Order> listOrders() {
        return orders;
    }
}



public class Driver {
    public static void main(String[] args) {
        RestaurantService restaurantService = new RestaurantService();
        OrderService orderService = new OrderService(restaurantService);

        restaurantService.addRestaurant("resta1", Map.of("burger", 10, "pizza", 20, "pasta", 15), 4);
        restaurantService.addRestaurant("resta2", Map.of("pizza", 18, "pasta", 13, "fries", 5), 3);

        orderService.placeOrder("cust1", Map.of("burger", 2, "pizza", 1));
        orderService.placeOrder("cust2", Map.of("pasta", 2));

        System.out.println("\nAll orders:");
        orderService.listOrders().forEach(System.out::println);

        orderService.markAsDelivered("order1");

        System.out.println("\nAll restaurants:");
        restaurantService.listRestaurants().forEach(System.out::println);
    }
}


