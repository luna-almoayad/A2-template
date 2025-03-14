package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONObject;

public class Controller{
    Drone d;
    POI POI;
    Translator translator;
    ExplorerPhase actionPhase;
    Phases dronePhase;

    public Controller(int battery){
        this.d = new Drone(battery);
       // POI = new PointsOfInterest();
        translator = new Translator();
        actionPhase = new ExploreGround();
    }

    public String getDecision(){
        return actionPhase.getDecision(d);
    }

    public void getResponse(JSONObject response){
        boolean switchPhases = actionPhase.getResponse(response, d);

        if(switchPhases && dronePhase == Phases.GROUND){
            dronePhase.switchPhase();
            actionPhase= new ExploreGround();
        }else if(switchPhases && dronePhase == Phases.GRIDSEARCH){
            dronePhase.switchPhase();
            actionPhase= new UTurn();
        }else if(switchPhases && dronePhase == Phases.UTURN){
            dronePhase.switchPhase();
            actionPhase= new ExploreForward();
        }

    }


    }
