package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

enum forwardPhases{
    SCAN,
    FLY;
    
}

public class ExploreForward implements ExplorerPhase{
    private forwardPhases phase = forwardPhases.SCAN;
    private Translator translator = new Translator();
    private final Logger logger = LogManager.getLogger();
    private Drone d;

    

    public ExploreForward(Drone d){
        phase = forwardPhases.SCAN;
        this.d=d;
    }


    public boolean getResponse(JSONObject response){
        logger.info("shake"+phase);
        switch(phase){
            case SCAN -> {
                if(translator.getBiomes(response).equals("sites")){
                    EmergencySite esite= new EmergencySite(translator.getSiteIDs(response), d.getLocation());
                    d.addEmergencySite(esite);
                }
                if(translator.getBiomes(response).equals("creeks")){
                    Creeks creek = new Creeks(translator.getSiteIDs(response), d.getLocation());
                    d.addCreek(creek);
                }
                if(translator.hasOcean(response)){
                    logger.info("in here!");
                    return true;
                }
                phase = forwardPhases.FLY;
                break;
            }
            case FLY -> {
                phase = forwardPhases.SCAN;
                break;
            }
            default -> {
                return false;
            }
        }
        return false; 
    } 

    public String getDecision(){
        switch(phase){
            case SCAN -> {
                return translator.scan();
            }

            case FLY -> {
                d.fly();
                return translator.fly();
            }
            default -> {
                return "Default";
            }
        }
    }

    

}