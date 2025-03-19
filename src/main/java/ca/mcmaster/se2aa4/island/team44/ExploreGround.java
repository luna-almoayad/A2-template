package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

enum State{
    L_ECHO,
    R_ECHO,
    F_ECHO,
    FLY,
    FOUNDGROUND,
    ONCOAST,
    STOP,
    E,
    STOP1,
    STOP2,
    UTURN1,
    UTURN2,
    UTURN3,
    UTURN4;
    
}

public class ExploreGround implements ExplorerPhase{

    private State state = State.FLY;
    protected JSONTranslator translate = new Translator();
    private int groundDistance;
    private Compass groundDirection;
    private final Logger logger = LogManager.getLogger();
    Drone d;
    private Compass start;
    private Compass foundDir;

    public ExploreGround(Drone d){
        this.d=d;
        start=d.getDirection();
        foundDir = d.getDirection();
    }

    @Override
    public boolean getResponse(JSONObject response)
    {
        try{
            

        }catch( Exception e)
        {

        }   

        switch(this.state){
        case State.FOUNDGROUND -> {
            if(groundDistance ==0) state = State.ONCOAST;
            break;
        }
        case State.FLY -> {
            state = State.F_ECHO;
            break;
            }

        case State.F_ECHO -> {
            if(translate.getFound(response).equals("GROUND")){
                state = State.FOUNDGROUND;
                groundDistance = translate.getRange(response);
            }
            else
                state = State.L_ECHO;
            break;
            }

        case State.L_ECHO -> {
            if(translate.getFound(response).equals("GROUND")){
                state = State.FOUNDGROUND;
                groundDistance = translate.getRange(response);
            } 
            else  
                state = State.R_ECHO;
            break;
            }

        case State.R_ECHO -> {
            if(translate.getFound(response).equals("GROUND")){
                state = State.FOUNDGROUND;
                groundDistance = translate.getRange(response);
            }
            else
                state = State.FLY;
            break;
            }

        case State.ONCOAST -> {
            state = State.STOP1;
            break;
            }
        case State.STOP1 ->{
            state = State.STOP2;
            break;
        }
        case State.UTURN1->{
            state = State.UTURN2;
            break;
        }
        case State.UTURN2->{
            state = State.UTURN3;
            break;
        }
        case State.UTURN3->{
            state = State.UTURN4;
            break;
        }
        case State.UTURN4->{
            state = State.FLY;
            break;
        }
        default ->{
            break;
        }
          
    }
    d.deductCost(translate.getCost(response));
    logger.info(d.checkBattery());
    return this.state==State.ONCOAST;
    }


    @Override
    public String getDecision(){

    switch(state){
        case State.FOUNDGROUND -> {
            if(groundDirection != d.getDirection()){
                d.setDirection(groundDirection);
                return translate.heading(groundDirection); 
            }
            groundDistance = groundDistance - 1;
            d.fly();
            return translate.fly();
            }

        case State.FLY -> {
            d.fly();
            return translate.fly();
            }

        case State.F_ECHO -> {
            groundDirection = d.getDirection();
            return translate.echo(d.getDirection());
            }

        case State.L_ECHO -> {
            groundDirection = d.getDirection().left();
            return translate.echo(d.getDirection().left());
            }

        case State.R_ECHO -> {
            groundDirection = d.getDirection().right();
            return translate.echo(d.getDirection().right());
            }

        case State.ONCOAST -> {
            return translate.scan();
            }
        case State.STOP1 -> {
            return translate.stop();
            }
        case State.STOP2 ->{
            return null;
        }
        case State.UTURN1 ->{
            d.fly();
            return translate.fly();
        }
        case State.UTURN2->{
            if(foundDir==d.getDirection().right())
                d.right();
            else
                d.left();
            return translate.heading(d.getDirection());
        }
        case State.UTURN3->{
            if(foundDir==d.getDirection().right())
                d.right();
            else
                d.left();
            return translate.heading(d.getDirection());
        }
        case State.UTURN4->{
            if(foundDir==d.getDirection().right())
                d.left();
            else
                d.right();
            return translate.heading(d.getDirection());
        }
        
        default -> {
            return "Default";
            }

        
    }
 

    }
         
}