package ca.mcmaster.se2aa4.island.team44.explorer;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team44.drones.Drone;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataAdapter;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataParser;
import ca.mcmaster.se2aa4.island.team44.site.Creek;
import ca.mcmaster.se2aa4.island.team44.site.EmergencySite;

enum forwardPhases{
    SCAN,
    FLY,
}

public class ExploreForward implements ExplorerPhase{
    private forwardPhases phase = forwardPhases.SCAN;
    private JSONDataAdapter translator = new JSONDataParser();
    private Drone d;

    

    public ExploreForward(Drone d){
        phase = forwardPhases.SCAN;
        this.d=d;
    }

    @Override
    public boolean getResponse(JSONObject response){
        
        //Deduct cost from battery 
        d.deductCost(translator.getCost(response));
    
        if(this.phase == forwardPhases.SCAN){
            if(translator.getSiteIDs(response)!=null){
                    EmergencySite esite= new EmergencySite(translator.getSiteIDs(response), d.getLocation());
                    d.addEmergencySite(esite);
                }

            if(translator.getCreekIDs(response)!=null){
                    Creek creek = new Creek(translator.getCreekIDs(response), d.getLocation());
                    d.addCreek(creek);
                }
                
            if(translator.hasOcean(response)){
                    return true;    
            }else{
                    phase = forwardPhases.FLY;
            }
        }else if(this.phase == forwardPhases.FLY){
            phase = forwardPhases.SCAN;
        }  
        return false;
    } 

    public String getDecision(){
        //Stop if Battery Low
        if(!d.sufficientBattery()){
            return d.stop();
        }
        if (phase == forwardPhases.SCAN) {
             return d.scan();
        } else if (phase == forwardPhases.FLY) {
            return d.fly();
        }
        return d.stop();
    }

}