package diningsim;

/**
 * MenuItem
 * Represents a menu item in the dining hall.
 * ID, name, price and category
 */
public class MenuItem {
    // The information evry menu item keeps.
  private String itemId;     
  private String name;      
  private double price;      
  private String category; 
  
  // Constructor
    public MenuItem(String itemId, String name, double price, String category) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.category = category;
    }
    // Getters
    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    // Setters

    // works on the price
public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    //works on the cost of several pieces of the same item. It multiplies the price by the quantity and returns the total cost.
   public double getCostFor(int quantity) {
        return price * quantity;
    } 
}
