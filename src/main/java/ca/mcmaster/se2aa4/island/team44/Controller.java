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
    POI POI;
    JSONTranslator translator;
    ExplorerPhase actionPhase;
    Phases dronePhase;
    private MissionReport report;
    private final Logger logger = LogManager.getLogger();



    public Controller(int battery, Compass direction){
        this.d = new Drone(battery, direction);
        translator = new Translator();
        actionPhase = new ExploreSpawn(d);
        dronePhase = Phases.SPAWN;
        report= new MissionReport(d);
    }

    

    public String getDecision(){
        return actionPhase.getDecision();
    }

    public void getResponse(JSONObject response){
        boolean switchPhases = actionPhase.getResponse(response);
        logger.info("switchPhase:" + switchPhases);
        logger.info("Drone stuff: "+d);

        if(switchPhases && dronePhase ==Phases.SPAWN){
            dronePhase = dronePhase.switchPhase();
            actionPhase= new ExploreGround(d);
        }
        else if(switchPhases && dronePhase == Phases.GROUND){
            dronePhase = dronePhase.switchPhase();
            actionPhase= new ExploreForward(d);
            logger.info("switching: " + dronePhase.toString());
        }else if(switchPhases && dronePhase == Phases.GRIDSEARCH){
            dronePhase = dronePhase.switchPhase();
            actionPhase= new ExploreTurn(d);
        }else if(switchPhases && dronePhase == Phases.UTURN){
            dronePhase = dronePhase.switchPhase();
            actionPhase= new ExploreForward(d);
        }

        logger.info("Phase: " + dronePhase.toString());

    }

    public String finishMission(){
        return report.generateReport();
    }


    }
