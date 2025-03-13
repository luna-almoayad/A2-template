package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class ExploreGround implements ExplorerPhase{


    private GroundState state = GroundState.FLY;

    private final Logger logger = LogManager.getLogger();

    protected Translator translate = new Translator();


    //WILL BE THE DRONE 

    Compass d = Compass.E;


    private int groundDistance;
    private Compass groundDirection;


    @Override
    public void readDecision(JSONObject response)
    {
        //Check if found Ground
        try{
            String found = translate.getFound(response);
            if(found.equals("GROUND")){
                state = GroundState.FOUNDGROUND;
                groundDistance = translate.getRange(response);
            }
        }catch( Exception e)
        {//

        }

        //if(state == GroundState.FOUNDGROUND){
            

        //}
    }


    public String getDecision(){

        ///WILLL CHANGE TO SWITCH STATE,
    switch(state){
        case GroundState.FOUNDGROUND: 

            if(groundDirection != d)
                return translate.heading(groundDirection); //

            if( groundDistance !=0) {
                groundDistance = groundDistance -1;
                return translate.fly();
            }
            return translate.stop();

        case GroundState.FLY:

            state = GroundState.F_ECHO;
            return translate.fly();

        case GroundState.F_ECHO:

            groundDirection = d;
            state = GroundState.L_ECHO;
            return translate.echo(d);

        case GroundState.L_ECHO:

            state = GroundState.R_ECHO;
            groundDirection = d.left();
            return translate.echo(d.left() );

        case GroundState.R_ECHO:

            state = GroundState.FLY;
            groundDirection = d.right();
            return translate.echo(d.right());

        default:
                return "Default";

        
    }


    //Read the direction of the drone, (compass now), and return echo to the left right and straight

    /*
    if(state == GroundState.FOUNDGROUND){
        return translate.stop();
    }
    else if(state == GroundState.FLY){
        state = GroundState.ECHO;
        return translate.fly();
    }else if(state == GroundState.ECHOS){
        state = GroundState.FLY;
        return translate.echo(Compass.SOUTH);
    }else if(state == GroundState.ECHON){
        state = GroundState.FLY;
        return translate.echo(Compass.NORTH);
        }else if(state == GroundState.ECHOE){
        state = GroundState.FLY;
        return translate.echo(Compass.EAST);
    return "idl";

     */

    }




/*
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
    }*/

         
}