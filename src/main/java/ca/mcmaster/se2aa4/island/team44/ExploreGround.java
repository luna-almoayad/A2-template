package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class ExploreGround implements ExplorerPhase{


    private State state = State.FLY;

    private final Logger logger = LogManager.getLogger();

    protected Translator translate = new Translator();

    private int groundDistance;
    private Compass groundDirection;


    @Override
    public boolean getResponse(JSONObject response)
    {
        try{
            String found = translate.getFound(response);
            if(found.equals("GROUND")){
                state = State.FOUNDGROUND;
                groundDistance = translate.getRange(response);
            }

        }catch( Exception e)
        {

        }       
        return state ==State.STOP;


    }


    public String getDecision(Drone d){

    switch(state){
        case State.FOUNDGROUND -> {

            if(groundDirection != d.getDirection()){
                d.setDirection(groundDirection);
                return translate.heading(groundDirection); //
            }
            
            groundDistance = groundDistance - 1;
            if( groundDistance !=0) {
            }else state = State.ONCOAST;
            return translate.fly();
            }

        case State.FLY -> {
            state = State.F_ECHO;
            return translate.fly();
            }

        case State.F_ECHO -> {
            groundDirection = d.getDirection();
            state = State.L_ECHO;
            return translate.echo(d.getDirection());
            }

        case State.L_ECHO -> {
            state = State.R_ECHO;
            groundDirection = d.getDirection().left();
            return translate.echo(d.getDirection().left());
            }

        case State.R_ECHO -> {
            state = State.FLY;
            groundDirection = d.getDirection().right();
            return translate.echo(d.getDirection().right());
            }

        case State.ONCOAST -> {
            state = State.STOP;
            return translate.scan();
            }
        case State.STOP -> {
            return translate.stop();
            }
        default -> {
            return "Default";
            }

        
    }
 

    }
         
}
