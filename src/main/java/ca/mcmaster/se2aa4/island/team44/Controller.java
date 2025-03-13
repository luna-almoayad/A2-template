package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONObject;

public class Controller{
    Drone drone;
    POI POI;
    Translator translator;
    ExplorerPhase phase;

    public Controller(int battery){
        drone = new Drone(battery);
       // POI = new PointsOfInterest();
        translator = new Translator();
        phase = new ExploreGround();
    }

    public String getDecision(){
        return phase.getDecision(drone);
    }

    public boolean getResponse(JSONObject response){

        return phase.getResponse(response);

       // phase.get_response(s, d);
    }


    }
