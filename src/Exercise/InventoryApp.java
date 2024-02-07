package Exercise;


import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Product {
  private String name;
  private double price;
  private int quantity;

  // Unchanged constructor
  public Product(String name, double price, int quantity) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
  }

  // Unchanged toString method
  @Override
  public String toString() {
    return "\nProduct " + name + ", price: " + price + ", quantity: " + quantity + "\n";
  }

  // Unchanged getter methods
  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public int getQuantity() {
    return quantity;
  }

  // Unchanged updatePrice method
  public void updatePrice(double newPrice) {
    this.price = newPrice;
  }
}

class Inventory {
  private List<Product> products;

  // Changed the constructor to use an immutable list
  public Inventory() {
    this.products = List.of(new Product("Laptop", 1000.0, 5)
        ,new Product("Smartphone", 500.0, 10)
        ,new Product("Tablet", 300.0, 8));
  }

  // Unchanged addProduct method
  public void addProduct(Product product) {
    products.add(product);
  }

  // Unchanged displayProducts method
  public void displayProducts() {
    System.out.println("Inventory:");
    products.forEach(System.out::println);
  }

  // Unchanged updatePrices method
  public void updatePrices(double percentageIncrease) {
    products.forEach(p -> p.updatePrice(p.getPrice() * (1 + percentageIncrease / 100)));
  }

  // Unchanged findProductsByPriceRange method
  public List<Product> findProductsByPriceRange(double minPrice, double maxPrice) {
    return products.stream().filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
        .collect(Collectors.toList());
  }

  // New method to remove products with quantity lower than a specified value
  public List<Product> removeLowQuantityProducts(int filterValue) {
    return products.stream().filter(p -> p.getQuantity() > filterValue).collect(Collectors.toList());
  }

  // New method to remove a product by name
  public void removeProduct(String productName) {
    products.removeIf(product -> product.getName().equalsIgnoreCase(productName));
  }
}

public class InventoryApp {
  public static void main(String[] args) {
    Inventory inventory = new Inventory();
    menuMethod(inventory);
  }

  private static void menuMethod(Inventory inventory) {
    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        System.out.println("1. Display Products\n2. Update Prices\n3. Find Products by Price Range\n"
            + "4. Display products with high availability\n5. Add product to inventory\n"
            + "6. Remove product from inventory\n7. Exit");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        
        scanner.nextLine(); // Consume newline
        switch (choice) {
        case 1 -> inventory.displayProducts();
        case 2 -> updatePrices(inventory, scanner);
        case 3 -> findProductsByPriceRange(inventory, scanner);
        case 4 -> filterProductWithLowQuantity(inventory, scanner);
        case 5 -> addNewProduct(inventory, scanner);
        case 6 -> removeProduct(inventory, scanner);
        case 7 -> {
          System.out.println("Exiting the inventory management system. Goodbye!");
          return;
        }
        default -> System.out.println("Invalid choice. Please enter a valid option.");
        }
      }
    }
  }

  // Unchanged updatePrices method
  private static void updatePrices(Inventory inventory, Scanner scanner) {
    System.out.print("Enter the percentage increase: ");
    double percentageIncrease = scanner.nextDouble();
    inventory.updatePrices(percentageIncrease);
    System.out.println("Prices updated.");
  }

  // Unchanged findProductsByPriceRange method
  private static void findProductsByPriceRange(Inventory inventory, Scanner scanner) {
    System.out.print("Enter the minimum price: ");
    double minPrice = scanner.nextDouble();
    System.out.print("Enter the maximum price: ");
    double maxPrice = scanner.nextDouble();
    List<Product> productsInRange = inventory.findProductsByPriceRange(minPrice, maxPrice);
    System.out.println("Products in the specified price range:");
    productsInRange.forEach(product -> System.out.println(product.getName() + " - Price: " + product.getPrice()));
  }

  // New method to filter and display products with low quantity
  private static void filterProductWithLowQuantity(Inventory inventory, Scanner scanner) {
    int value = 2;
    List<Product> lowQuantityRemoved = inventory.removeLowQuantityProducts(value);
    lowQuantityRemoved.forEach(System.out::println);
  }

  // New method to add a new product to the inventory
  private static void addNewProduct(Inventory inventory, Scanner scanner) {
    System.out.print("Enter the product name: ");
    String name = scanner.nextLine();
    System.out.print("Enter the product price: ");
    double price = scanner.nextDouble();
    System.out.print("Enter the product quantity: ");
    int quantity = scanner.nextInt();
    inventory.addProduct(new Product(name, price, quantity));
    System.out.println("New product added to the inventory.");
  }

  // New method to remove a product from the inventory
  private static void removeProduct(Inventory inventory, Scanner scanner) {
    System.out.print("Enter the name of the product to remove: ");
    String productName = scanner.nextLine();
    inventory.removeProduct(productName);
    System.out.println("Product removed from the inventory.");
  }
}