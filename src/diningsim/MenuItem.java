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
}
