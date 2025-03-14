package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONObject;

public class ExploreCoast implements ExplorerPhase {
  
    private Translator translator= new Translator();
    private State state = State.ONCOAST;
    private Compass compass; 

    Drone drone;

    public ExploreCoast(Drone drone){
        this.drone= drone; 
        this.compass= drone.getDirection();
    }

     @Override
    public boolean getResponse(JSONObject response, Drone d) {
        return true;


    }

    @Override 
    public String getDecision(Drone d){

        return "hello";
            
    }
}

