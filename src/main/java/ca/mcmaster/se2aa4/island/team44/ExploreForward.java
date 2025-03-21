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
//FLY2 and SCAN 2 is unreachable ask sama.

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
        
        //Deduct cost from battery 
        d.deductCost(translator.getCost(response));
        logger.info("**Battery" + d.checkBattery());


        if(this.phase == forwardPhases.SCAN){
            if(translator.getSiteIDs(response)!=null){
                    EmergencySite esite= new EmergencySite(translator.getSiteIDs(response), d.getLocation());
                    d.addEmergencySite(esite);
                }

            if(translator.getCreekIDs(response)!=null){
                    Creeks creek = new Creeks(translator.getCreekIDs(response), d.getLocation());
                    d.addCreek(creek);
                }
                
            if(translator.hasOcean(response))
                    return true;    
            else
                    phase = forwardPhases.FLY;

        }else if(this.phase == forwardPhases.FLY)
            phase = forwardPhases.SCAN;

        else if(phase == forwardPhases.FLY2) 
            phase = forwardPhases.SCAN2;

        else if(phase == forwardPhases.SCAN2)
            return true;
        
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