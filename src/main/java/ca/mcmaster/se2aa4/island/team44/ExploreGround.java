package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


public class ExploreGround implements ExplorerPhase{

    private boolean foundGround = false;
    private boolean fly = true;
    private boolean stop = false;

    private final Logger logger = LogManager.getLogger();


    private int distance = 0;



    @Override
    public void readDecision(JSONObject response, JSONObject extraInfo)
    {
        if(extraInfo.has("found")){
            if( extraInfo.getString("found").equals("GROUND") ){
                foundGround = true; //turn that direction
                distance = extraInfo.getInt("range");
            }
                
            }
    }

    @Override
    public JSONObject getDecision(){
        JSONObject decision = new JSONObject();
        if(stop){
            decision.put("action","stop");
        }
        else if(distance != 0)
        {
            decision.put("action","fly");
            distance--;
            if(distance <1){
                stop = true;
            }
        }
        else if(foundGround){
            decision.put("action","heading");  
            decision.put("parameters", new JSONObject().put("direction", "S"));
            // go to emergency site
        }
        else if(fly){
            decision.put("action","fly");
            fly= false;
        }else{
            decision.put("action","echo");
            decision.put("parameters", new JSONObject().put("direction", "S"));
            fly = true;
        }
        logger.info(decision.toString());

        return decision;
    }

         
}