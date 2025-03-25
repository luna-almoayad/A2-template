package ca.mcmaster.se2aa4.island.team44.control;

import org.json.JSONObject;
import ca.mcmaster.se2aa4.island.team44.drones.Drone;
import ca.mcmaster.se2aa4.island.team44.explorer.ExploreSpin;
import ca.mcmaster.se2aa4.island.team44.explorer.ExplorerFactory;
import ca.mcmaster.se2aa4.island.team44.explorer.ExplorerPhase;
import ca.mcmaster.se2aa4.island.team44.explorer.Phases;
import ca.mcmaster.se2aa4.island.team44.navigation.Compass;

// Controller class to handle switching between exploration phases 
public class Controller{
    public Drone d;
    public ExplorerPhase actionPhase;
    public Phases dronePhase;
    private final ExplorerFactory exploreFactory = new ExplorerFactory();
    public MissionReport report;

    // constructor to initialize necessary objects 
    public Controller(int battery, Compass direction){
        this.d = new Drone(battery, direction);
        actionPhase = new ExploreSpin(d);
        dronePhase = Phases.SPIN;
        report= new MissionReport(d);
    }
    
    // getDecision method that communicates with each exploration phase 
    public String getDecision(){
        return actionPhase.getDecision();
    }

    //getResponse from each of the exploration methods 
    public void getResponse(JSONObject response){
        boolean switchPhases = actionPhase.getResponse(response);

        //Implements Factory Pattern to switch phases 
        if(switchPhases){
            dronePhase = dronePhase.switchPhase();
            actionPhase= exploreFactory.getPhase(dronePhase, d);
        }
    }

    // Once mission is complete, output the mission report 
    public String finishMission(){
        return report.generateReport();
    }

}
