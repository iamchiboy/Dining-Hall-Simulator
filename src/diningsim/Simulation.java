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


    public void startSimulation(){
        simulationRunning = true;
    }

    public void stopSimulation(){
        simulationRunning = false;
    }

    public boolean isRunning(){
        return simulationRunning;
    }


    //creates the student object and performs the order
    public Order placeOrderFor(String studentName, String studentId, Map<MenuItem, Integer> items){
        if( studentName == null || studentName.isEmpty()){
            throw new IllegalArgumentException("Student Name is required");}
        Student student = new Student(studentId, studentName.trim());
        Order order = student.placeOrder(items);
        submitOrder(order);
        return order;

    }


    public void submitOrder(Order order){
        String id = order.getStudent().getStudentId();
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

    //calls each counter to serve the student in front of their queue
    private void startServices(){
        for(Counter counter: queueManager.getAllCounters()){
            counter.serveNextOrder(clock);
        }
    }


    private void finishServices(){
        for(Counter counter: queueManager.getAllCounters()){
            Order done = counter.workOneMinute(); //returns null if order is not finished
            if (done == null){continue;}
            boolean hasMore = queueManager.moveToNextCounter(done,clock);
            if (!hasMore){
                totalStudentsServed++;
                activeStudentIds.remove(done.getStudent().getStudentId());

            }
        }
    }


    public int getClock(){return clock;};

    public int getTotalStudentsServed(){return totalStudentsServed;};

    public int getQueueLength(String type){
        return queueManager.getQueueLength(type);
    }

    public double getOverallAverageWaitingTime(){
        return queueManager.getOverallAverageWaititngTime();
    }

    public double getAverageWaitingTime(String type){
        return queueManager.getCounter(type).calculateAverageWaitingTime();
    }

    //builds the statistics reports
    public String getSummaryReport(){
        StringBuilder report = new StringBuilder();
        report.append("Simulated time: ").append(clock).append(" min\n");
        report.append("Students served: ").append(totalStudentsServed).append("\n");

        for (Counter counter: queueManager.getAllCounters()){
            report.append(counter.getType())
                    .append(" - Avg wait: ")
                    .append(String.format("%.1f", counter.calculateAverageWaitingTime()))
                    .append(" min, Queue length: ")
                    .append(counter.getQueueLength())
                    .append("\n");
        }

        report.append(String.format("Overall average wait: %.1f min", getOverallAverageWaitingTime()));
        return report.toString();
    }









}
