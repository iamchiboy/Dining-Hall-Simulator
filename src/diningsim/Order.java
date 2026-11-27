package diningsim;

import java.util.ArrayList; // For the list you create. Used in the "counterSequence = new ArrayList<>();"
import java.util.Collections; // Helps in making read-only versions. used in the "Collections.unmodifiableMap(items)."
import java.util.LinkedHashMap; // Remembers the order items were made. Used in the "items = new LinkedHasMap(items)."
import java.util.List; // Items in order. Used in the "private List<String> counterSequence;"
import java.util.Map; //A set of pairs / item quantity. Used in "private Map<MenuItem, Integer> items;"

/**
 * Order
 * Represents an order in the dining hall.
 * Contains a list of menu items.
 * It holds the items with quantities, works out which
 * counters to visit and in what order, and 
 * remembers which counter the student is at now.
 */


public class Order {

private String orderId;                   
private Student student;  
private Map<MenuItem, Integer> items; // store pairs. For example item and quantity.
private List<String> counterSequence; // List of countr names in the order they will be visited.
private int currentCounterIndex; // The position in the counterSequence list that the student is at now. 
private int enqueueTime; // The time the student was added to the queue. Used to work out the waiting time.

// Constructor; to run when a new order is created.
public Order (String orderId, Student student, Map<MenuItem, Integer> items){
    
 // to ensure the order must have one item.
 if(items == null || items.isEmpty()){
    throw new IllegalArgumentException("Order must contain at least one item");
 }  
 //ensuring every quantity must be between 1 and 10.
 for(int quantity : items.values()){
    if(quantity < Constants.MIN_QUANTITY  || quantity > Constants.MAX_QUANTITY){
        throw new IllegalArgumentException("Quantity for each item must be between 1 and 10");
    }
 }

 //Stores the values
this.orderId = orderId;
this.student = student;

// we make our own copy of the items, so that nobody can change the item latter.
this.items = new LinkedHashMap<>(items); // LinkedHashMap to remember the order items were added.
this.counterSequence = new ArrayList<>(); // Create a new list to hold the counter sequence.
this.currentCounterIndex = 0; // Start at the first counter in the sequence.
this.enqueueTime = 0; // Initialize enqueue time to 0. It will be set when the order is added to the queue.

 // work out which counter to visit
 buildCounterSequence();

 //Fills the counterSequence with each category once,
 //in the order the catogories first apear in the ticked items.
 // For example, if the order has a meal and a drink, the counterSequence will be ["Meals", "Drinks"].
 public void buildCounterSequence(){
    counterSequence.clear();//start with an emty list.

    //Go through every item, in the order they were ticked.
    for (MenuItem : items.keySet()){
        String category = item.getCategory();

        //Only add the category if it is not in the list yet.
        if(!counterSequence.contains(category)){
            counterSequence.add(category)
        }

 }
}

//Returns the counter the student must go to now 
// returns null when all counters are finished.
public String getCurrentCounterType() {
    if (isCompleted()){
        return null;
    }
    return counterSequence.get(currentCounterIndex);
}
// returns only itens to be collected at the current counter
public Map<MenuItem, Integer> getItemsForCurrentCounter(){
   Map<MenuItem, Integer> result = new LinkedHashMap<>();
   String type = getCurrentCounterType();
   if(type == null);
   return result;

   for(Map.Entry<MenuItem, Integer> entry  : items.entrySet()); 
      if (entry.getKey().getCategory().equals(type)){
        result.put(entry.getKey(), entry.getValue());
      }
      return result;
}
}
}

 
 
 