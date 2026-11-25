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