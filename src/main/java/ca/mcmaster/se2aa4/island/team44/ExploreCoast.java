package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONObject;

public class ExploreCoast implements ExplorerPhase {
  
    private Translator translator= new Translator();
    private State state = State.ONCOAST;

    public ExploreCoast(Drone drone){
        this.drone= drone; 
    }

     @Override
    public void readDecision(JSONObject response) {


    }

    @Override 
    public String getDecision(){
        
            
    }
}

