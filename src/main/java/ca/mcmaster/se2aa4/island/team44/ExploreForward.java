package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

enum forwardPhases{
    SCAN,
    FLY,
    
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
        d.deductCost(translator.getCost(response));
        logger.info("**Battery" + d.checkBattery());
        logger.info("shake"+phase);
        switch(phase){
            case SCAN -> {
                if(translator.getSiteIDs(response)!=null){
                    EmergencySite esite= new EmergencySite(translator.getSiteIDs(response), d.getLocation());
                    d.addEmergencySite(esite);
                    logger.info("1223esite found");
                }
                if(translator.getCreekIDs(response)!=null){
                    Creeks creek = new Creeks(translator.getCreekIDs(response), d.getLocation());
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
            }

            default -> {
                return false;
            }
        }
        return false; 
    } 

    public String getDecision(){
        //Stop if Battery Low
        if(!d.sufficientBattery()){
        logger.info("**Low Battery: Returning to Base");
        return translator.stop();
        }
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