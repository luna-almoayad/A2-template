package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

enum forwardPhases{
    SCAN,
    FLY,
    FLY2,
    SCAN2;
    
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

    @Override
    public boolean getResponse(JSONObject response){
        logger.info("shake"+phase);
        switch(phase){
            case SCAN -> {
                if(translator.getSiteIDs(response)!=null){
                    EmergencySite esite= new EmergencySite(translator.getSiteIDs(response), d.getLocation());
                    d.addEmergencySite(esite);
                    logger.info("1223esite found");
                }
                if(translator.getCreekIDs(response)!=null){
                    Creeks creek = new Creeks(translator.getSiteIDs(response), d.getLocation());
                    d.addCreek(creek);
                    logger.info("lesh enta wein");
                }
                if(translator.hasOcean(response)){
                    logger.info("in here!");
                    return true;
                }
                else
                    phase = forwardPhases.FLY;
                break;
            }
            case FLY -> {
                phase = forwardPhases.SCAN;
                break;
            }case FLY2->{
                phase = forwardPhases.SCAN2;
                break;
            }
            case SCAN2 ->{
                return true;
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
            case FLY2 ->{
                d.fly();
                return translator.fly();
            }
            case SCAN2->{
                return translator.scan();
            }
            default -> {
                return "Default";
            }
        }
    }

    

}