package ca.mcmaster.se2aa4.island.team44;
import org.json.JSONObject;

public class Controller{
    Drone d;
    ExplorerPhase actionPhase;
    Phases dronePhase;
    private final ExplorerFactory exploreFactory = new ExplorerFactory();
    MissionReport report;


    public Controller(int battery, Compass direction){
        this.d = new Drone(battery, direction);
        actionPhase = new ExploreSpin(d);
        dronePhase = Phases.SPIN;
        report= new MissionReport(d);
    }
    

    public String getDecision(){
        return actionPhase.getDecision();
    }

    //Implements Factory Pattern
    public void getResponse(JSONObject response){
        boolean switchPhases = actionPhase.getResponse(response);

        if(switchPhases){
            dronePhase = dronePhase.switchPhase();
            actionPhase= exploreFactory.getPhase(dronePhase, d);
        }
    }

    public String finishMission(){
        return report.generateReport();
    }

}
