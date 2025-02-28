import java.util.*;

// Custom Exception for Out of Stock Scenario
class OutOfStockException extends Exception {
    public OutOfStockException(String message) {
        super(message);
    }
}

// Product.java
abstract class Product {
    protected int productID;
    protected String name;
    protected String category;
    protected double price;
    protected int stockQuantity;

    public Product(int productID, String name, String category, double price, int stockQuantity) {
        this.productID = productID;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public abstract double calculateDiscount();

    public void reduceStock(int quantity) throws OutOfStockException {
        if (stockQuantity < quantity) {
            throw new OutOfStockException("Product " + name + " is out of stock.");
        }
        stockQuantity -= quantity;
    }

    public double getPrice() {
        return price;
    }
}

// Electronics.java
class Electronics extends Product {
    private int warrantyPeriod;

    public Electronics(int productID, String name, double price, int stockQuantity, int warrantyPeriod) {
        super(productID, name, "Electronics", price, stockQuantity);
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public double calculateDiscount() {
        return price * 0.10; // 10% discount
    }
}

// Clothing.java
class Clothing extends Product {
    private String size;

    public Clothing(int productID, String name, double price, int stockQuantity, String size) {
        super(productID, name, "Clothing", price, stockQuantity);
        this.size = size;
    }

    @Override
    public double calculateDiscount() {
        return price * 0.15; // 15% discount
    }
}

// OrderDetails.java
class OrderDetails {
    private int orderDetailID;
    private int orderID;
    private int productID;
    private int quantity;
    private double subTotal;

    public OrderDetails(int orderDetailID, int orderID, Product product, int quantity) throws OutOfStockException {
        this.orderDetailID = orderDetailID;
        this.orderID = orderID;
        this.productID = product.productID;
        this.quantity = quantity;
        product.reduceStock(quantity);
        this.subTotal = (product.getPrice() - product.calculateDiscount()) * quantity;
    }

    public double getSubTotal() {
        return subTotal;
    }
}

// Order.java
class Order {
    private int orderID;
    private int userID;
    private List<OrderDetails> orderDetailsList;

    public Order(int orderID, int userID) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderDetailsList = new ArrayList<>();
    }

    public void addOrderDetail(OrderDetails orderDetail) {
        orderDetailsList.add(orderDetail);
    }

    public double calculateTotal() {
        return orderDetailsList.stream().mapToDouble(OrderDetails::getSubTotal).sum();
    }
}

// ECommerceApp.java
public class ECommerceApp {
    private static List<Order> orders = new ArrayList<>();
    private static Map<Integer, Order> orderHistory = new HashMap<>();
    private static List<Product> products = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeProducts();
        boolean running = true;
        while (running) {
            System.out.println("1. Place Order\n2. View Order History\n3. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    placeOrder();
                    break;
                case 2:
                    viewOrderHistory();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void initializeProducts() {
        products.add(new Electronics(1, "Laptop", 1000, 5, 2));
        products.add(new Clothing(2, "T-Shirt", 20, 10, "M"));
    }

    private static void placeOrder() {
        System.out.println("Enter User ID:");
        int userID = scanner.nextInt();
        Order order = new Order(orders.size() + 1, userID);
        boolean adding = true;
        while (adding) {
            System.out.println("Select Product:");
            for (int i = 0; i < products.size(); i++) {
                System.out.println((i + 1) + ". " + products.get(i).name + " - $" + products.get(i).getPrice());
            }
            int productChoice = scanner.nextInt() - 1;
            System.out.println("Enter Quantity:");
            int quantity = scanner.nextInt();
            try {
                OrderDetails details = new OrderDetails(orderHistory.size() + 1, orderHistory.size() + 1, products.get(productChoice), quantity);
                order.addOrderDetail(details);
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Add more items? (yes/no)");
            adding = scanner.next().equalsIgnoreCase("yes");
        }
        orders.add(order);
        orderHistory.put(userID, order);
        System.out.println("Order placed successfully. Total: $" + order.calculateTotal());
    }

    private static void viewOrderHistory() {
        System.out.println("Enter User ID:");
        int userID = scanner.nextInt();
        if (orderHistory.containsKey(userID)) {
            System.out.println("Total: $" + orderHistory.get(userID).calculateTotal());
        } else {
            System.out.println("No order history found.");
        }
    }
}
