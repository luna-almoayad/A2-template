package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class ExploreEnd implements ExplorerPhase {
    enum steps{
        FG,
        ECHOT, //change later pls
        END; 
    }
    private Drone d; 
    private Translator translator = new Translator(); 
    private final Logger logger = LogManager.getLogger();
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
            return translator.stop();
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
                   logger.info("hardtime");
                   logger.info("my creeks: "+d.getCreek());
                   logger.info("closest creek "+d.getClosestCreek());
                   logger.info("my site "+d.getESite());
                   logger.info("where");
                   logger.info("location to site: "+d.getLocation().calculateDistance(site.getLocation())+" site to closest: "+(site.getLocation().calculateDistance(closest.getLocation())));
                   if(Math.abs(d.getLocation().getXCoord()-site.getLocation().getXCoord())>(Math.abs(closest.getLocation().getXCoord()-site.getLocation().getXCoord()))){
                       logger.info("MAYBE bebebbe");
                       step = steps.END;
                   }

               }
           }
  

    return false;

       }

   }
 