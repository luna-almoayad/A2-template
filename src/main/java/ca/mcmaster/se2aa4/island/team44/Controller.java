package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
/*
for now bestie:
need to implent echo forward before u-turn to see if should fly to coast or u-turn
fly until coast range is 0 */

/*
another thing: 
make uturn happen earlier?
need to find dimensions of whole grid and making that our base cases for uturn
might need to scan at first fly in uturn*/ 

public class Controller{
    Drone d;
    ExplorerPhase actionPhase;
    Phases dronePhase;
    private final ExplorerFactory exploreFactory = new ExplorerFactory();
    private final Logger logger = LogManager.getLogger();
    private MissionReport report;



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
        logger.info("***SwitchPhase:" + switchPhases);
        logger.info("***Drone: "+d);

        if(switchPhases){
            dronePhase = dronePhase.switchPhase();
            actionPhase= exploreFactory.getPhase(dronePhase, d);
            logger.info("***Switching: " + dronePhase.toString());
        }
        logger.info("Phase: " + dronePhase.toString());

    }

    public String finishMission(){
        return report.generateReport();
    }


    }
