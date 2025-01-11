import java.util.*;
import java.text.SimpleDateFormat;

class Product {
    String name;
    double price;
    int stock;
    boolean onSale;

    public Product(String name, double price, int stock, boolean onSale) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.onSale = onSale;
    }

    @Override
    public String toString() {
        return "Product(name: " + name + ", price: " + price + ", stock: " + stock + ", onSale: " + onSale + ")";
    }
}

class User {
    String name;
    String email;
    Cart cart;
    boolean loggedIn;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.cart = new Cart();
        this.loggedIn = false;
    }

    public void logIn() {
        loggedIn = true;
        System.out.println(name + " logged in.");
    }

    public void logOut() {
        loggedIn = false;
        System.out.println(name + " logged out.");
    }

    @Override
    public String toString() {
        return "User(name: " + name + ", email: " + email + ", loggedIn: " + loggedIn + ")";
    }
}

class Cart {
    List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        if (product.stock >= quantity) {
            items.add(new CartItem(product, quantity));
            product.stock -= quantity;
            System.out.println("Added " + quantity + " of " + product.name + " to the cart.");
        } else {
            System.out.println("Not enough stock for " + product.name + ". Only " + product.stock + " available.");
        }
    }

    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            Product product = item.product;
            int quantity = item.quantity;
            if (!product.onSale) {
                total += product.price * quantity;
            } else {
                total += product.price * quantity; // No discount for on-sale items
            }
        }
        return total;
    }
}

class CartItem {
    Product product;
    int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}

class Order {
    User user;
    Cart cart;
    Date orderTime;
    double discount;
    boolean isCancelled;

    public Order(User user, Cart cart, double discount) {
        this.user = user;
        this.cart = cart;
        this.orderTime = new Date();
        this.discount = discount;
        this.isCancelled = false;
    }

    public double applyDiscount() {
        double total = cart.calculateTotal();
        if (discount > 0) {
            return total - (total * discount);
        }
        return total;
    }

    public void cancelOrder() {
        long diffInMillies = Math.abs(new Date().getTime() - orderTime.getTime());
        long diffInHours = diffInMillies / (60 * 60 * 1000);
        if (isCancelled) {
            System.out.println("Order is already cancelled.");
        } else if (diffInHours > 24) {
            System.out.println("Order cannot be cancelled after 24 hours.");
        } else {
            isCancelled = true;
            System.out.println("Order cancelled successfully.");
        }
    }

    @Override
    public String toString() {
        String status = isCancelled ? "Cancelled" : "Active";
        return "Order(user: " + user.name + ", total: " + applyDiscount() + ", status: " + status + ")";
    }
}

class OnlineShoppingSystem {
    public static void main(String[] args) {
        // Creating Products
        Product laptop = new Product("Laptop", 1000, 10, false);
        Product phone = new Product("Phone", 500, 20, true);

        // Creating User
        User user = new User("John Doe", "johndoe@example.com");
        user.logIn();

        // User adds items to cart
        user.cart.addItem(laptop, 2);
        user.cart.addItem(phone, 1);

        // Create Order
        Order order = new Order(user, user.cart, 0.1);  // 10% discount
        System.out.println(order);

        // Apply discount and calculate total
        double total = order.applyDiscount();
        System.out.println("Total after discount: " + total);

        // Cancel order within 24 hours
        order.cancelOrder();

        // Display the user's order
        System.out.println(order);

        user.logOut();
    }
}
