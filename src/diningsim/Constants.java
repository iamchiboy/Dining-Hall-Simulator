package diningsim;

/**
 * Constants
 * A class to hold shared constant values for the dining hall queue simulation.
 * It has no methods and is never created with new.
 */
public class Constants {
    //The name of the tree counters
    public static final String MEALS = "Meals";
    public static final String DRINKS = "Drinks";
    public static final String PASTRIES = "Pastries";

    //An array to hold ther three counter names. It helps when we will want to loop thriugh each counter.
public static final String[] CATEGORIES = { MEALS, DRINKS, PASTRIES }; 

//How long a minute lasts on the creen in miliseconds.
public static final int TICK_MILLIS = 1000;

// Service rate
public static final int MEALS_RATE = 5;
public static final int DRINKS_RATE = 1;
public static final int PASTRIES_RATE = 2;

    //The quantity a student can pick from one item.
public static final int MIN_QUANTITY = 1;
public static final int MAX_QUANTITY = 10;  
}
