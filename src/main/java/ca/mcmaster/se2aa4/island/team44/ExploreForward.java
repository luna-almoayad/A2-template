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
                else
                    phase = forwardPhases.FLY;
                break;
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
            }
        }
        return false; 
    } 

    public String getDecision(){
<<<<<<< HEAD
        switch(phase){
            case SCAN -> {
                return translator.scan();
            }
=======

<<<<<<< HEAD
        if (phase == forwardPhases.SCAN){
            return translator.scan();
        }
        else if (phase == forwardPhases.FLY){
            d.fly();
            return translator.fly();
        }
        else{
            return "Default";
=======
>>>>>>> af6c630ee4d82f5683f93b9277d41795dfc0d14f
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