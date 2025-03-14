package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Controller{
    Drone d;
    POI POI;
    Translator translator;
    ExplorerPhase actionPhase;
    Phases dronePhase;

    private final Logger logger = LogManager.getLogger();



    public Controller(int battery){
        this.d = new Drone(battery);
       // POI = new PointsOfInterest();
        translator = new Translator();
        actionPhase = new ExploreGround(d);
        dronePhase = Phases.GROUND;
    }

    public String getDecision(){
        return actionPhase.getDecision();
    }

    public void getResponse(JSONObject response){
        boolean switchPhases = actionPhase.getResponse(response);
        logger.info("switchPhase:" + switchPhases);
        logger.info("Drone stuff: "+d);

        if(switchPhases && dronePhase == Phases.GROUND){
           // logger.info("bebebbeb:");
            dronePhase = dronePhase.switchPhase();
            actionPhase= new ExploreForward(d);
        }else if(switchPhases && dronePhase == Phases.GRIDSEARCH){
            dronePhase = dronePhase.switchPhase();
            actionPhase= new UTurn(d);
        }else if(switchPhases && dronePhase == Phases.UTURN){
            dronePhase = dronePhase.switchPhase();
            actionPhase= new ExploreForward(d);
        }

        logger.info("Phase: " + dronePhase.toString());

    }


    }
