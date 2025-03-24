package ca.mcmaster.se2aa4.island.team44.explorer;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team44.drones.Drone;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataAdapter;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataParser;
import ca.mcmaster.se2aa4.island.team44.navigation.Location;

public class ExploreEnd implements ExplorerPhase {
    enum steps{
        FG,
        ECHOT, //change later pls
        END; 
    }
    private Drone d; 
    private JSONDataAdapter translator = new JSONDataParser(); 
    steps step = steps.ECHOT;
    private int groundDistance;


    public ExploreEnd(Drone d){
        this.d=d;
    }
    public String getDecision(){
        if(step == steps.FG) {
            return d.fly();
               
        }else if(step == steps.ECHOT) {
            return d.echo("F");
        }else {
            return d.stop();
        }
    }

    public boolean getResponse(JSONObject response){
        if(step == steps.FG) {
            if(groundDistance==1 || groundDistance==0){
                return true;
            }
               groundDistance--;
        }else if(step == steps.ECHOT) {
            if(!d.isGround(response)){
                step = steps.END;
               } 
               else{
                   groundDistance=translator.getRange(response);
                   step = steps.FG;
               }

               if(d.ifPossiblyFound()){
                   Location closest = d.getClosestCreek().getLocation();
                   Location site= d.getESite().getLocation();
                   if(Math.abs(d.getLocation().getXCoord()-site.getXCoord())>(Math.abs(closest.getXCoord()-site.getXCoord()))){
                       step = steps.END;
                   }

               }
           }
    return false;

       }

   }
 