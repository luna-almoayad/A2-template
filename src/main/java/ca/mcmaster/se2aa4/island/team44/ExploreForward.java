package ca.mcmaster.se2aa4.island.team44;

import java.util.*;
import org.json.JSONArray;
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
    private Compass direction;

    public boolean getResponse(JSONObject response, Drone d){
        String found = translator.getFound(response);
        if(translator.getFound(response).equals("OUT_OF_RANGE")){
            phase = forwardPhases.STOP;
        }
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
            }
            case F_ECHO -> {
                direction = d.getDirection();
                phase = forwardPhases.L_ECHO;
                if(translator.getFound(response).equals("OUT_OF_RANGE"))
                    return true;
            }
            case L_ECHO -> {
                phase = forwardPhases.R_ECHO;
                direction = d.getDirection().left();
                if(translator.getFound(response).equals("OUT_OF_RANGE"))
                    phase = forwardPhases.STOP;
            }
            case R_ECHO -> {
                phase = forwardPhases.FLY;
                direction = d.getDirection().right();
                if(translator.getFound(response).equals("OUT_OF_RANGE"))
                    phase = forwardPhases.STOP;
            }
            case FLY -> {
                phase = forwardPhases.F_ECHO;
            }
            case STOP -> {
                phase = forwardPhases.STOP;
            }
            default -> {
                return false;
            }
        }
        return false; 
    } 

    public String getDecision(Drone d){
        switch(phase){
            case SCAN -> {
                return translator.scan();
            }
            case F_ECHO -> {
                return translator.echo(direction);
            }
            case L_ECHO -> {
                direction = d.getDirection().left();
                return translator.echo(direction);
            }
            case R_ECHO -> {
                direction = d.getDirection().right();
                return translator.echo(direction);
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