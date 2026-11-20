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





}
