package diningsim;

/**
 * Simulation
 * Represents the dining hall queue simulation.
 * It manages the students, counters, and queues.
 * It is responsible for running the simulation and reporting statistics.
 * It owns the clock,accept orders and moves time forward one minute per tick.
 */

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Simulation {
    private QueueManager queueManager;
    private int clock;
    private boolean simulationRunning;
    private int totalStudentsServed;
    private Set<String> activeStudentIds;  //stores IDs of students who have unfinished orders


    public Simulation (QueueManager queueManager){
        this.queueManager = queueManager;
        this.clock = 0;
        this.simulationRunning = false;
        this.totalStudentsServed = 0;
        this.activeStudentIds = new HashSet<>();

    }

    //method to start the simulation
    public void startSimulation(){
        simulationRunning = true;
    }

    //stop simulation
    public void stopSimulation(){
        simulationRunning = false;
    }

    //check simulation status
    public boolean isRunning(){
        return simulationRunning;
    }


    //creates the student object and performs the order
    public Order placeOrderFor(String studentName, String studentId, Map<MenuItem, Integer> items){
        if( studentName == null || studentName.isEmpty()){
            throw new IllegalArgumentException("Student Name is required");}
        Student student = new Student(studentId, studentName.trim());

        Order order = student.placeOrder(items);
        //submitOrder
        submitOrder(order);
        return order;

    }


    public void submitOrder(Order order){
        String id = order.getStudent().getStudentId();

        //students with unfinished orders can not order again unless when done
        if(activeStudentIds.contains(id)){
            throw new IllegalArgumentException("Student" + id + " already has an order\n" +
                    "in a queue. Wait until it is finished.");}

        //puts an order at the back of the queue of the counter it needs now
        queueManager.routeOrder(order, clock);
        activeStudentIds.add(id);

    }

    //moves the simulation forward by 1 minute
    public void tick(){
        if (!simulationRunning){return;}
        startServices();
        clock++;
        finishServices();
    }







}
