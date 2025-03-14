package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

enum forwardPhases{
    SCAN,
    L_ECHO,
    R_ECHO,
    F_ECHO,
    FLY,
    STOP;
    
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
                phase = forwardPhases.FLY;

                break;
            }
            case F_ECHO -> {
                phase = forwardPhases.SCAN;
                logger.info("baboom"+translator.getFound(response));
                if(translator.getFound(response).equals("OUT_OF_RANGE")){
                    return true;
                }

                break;
                    
            }
            case L_ECHO -> {
                phase = forwardPhases.R_ECHO;
                if(translator.getFound(response).equals("OUT_OF_RANGE")){
                    //phase = forwardPhases.STOP;
                }

                break;
            }
            case R_ECHO -> {
                phase = forwardPhases.SCAN;
                if(translator.getFound(response).equals("OUT_OF_RANGE"))
                    //phase = forwardPhases.STOP;

                break;
            }
            case FLY -> {
                phase = forwardPhases.F_ECHO;
                break;
            }
            case STOP -> {
                phase = forwardPhases.STOP;
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
            case F_ECHO -> {
                return translator.echo(d.getDirection());
            }
            case L_ECHO -> {
                return translator.echo(d.getDirection().left());
            }
            case R_ECHO -> {
                return translator.echo(d.getDirection().right());
            }
            case FLY -> {
                d.fly();
                return translator.fly();
            }
            case STOP -> {
                return translator.stop();
            }
            default -> {
                return "Default";
            }
        }
    }

    

}