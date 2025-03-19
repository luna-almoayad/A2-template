package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONObject;

enum State{
    L_ECHO,
    R_ECHO,
    F_ECHO,
    FLY,
    FOUNDGROUND,
    FLYTOGROUND,
    ONCOAST,
    STOP,
    E,
    CHANGEHEADING,
    STOP1,
    STOP2;
    
}

public class ExploreGround implements ExplorerPhase{

    private State state = State.FLY;
    protected Translator translate = new Translator();
    private int groundDistance;
    private Compass groundDirection;
    Drone d;

    public ExploreGround(Drone d){
        this.d=d;
    }

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
            state = State.L_ECHO;
            break;
            }

        case State.L_ECHO -> {
            state = State.R_ECHO;
            break;
            }

        case State.R_ECHO -> {
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
        default ->{
            break;
        }
          
    }
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
        default -> {
            return "Default";
            }

        
    }
 

    }
         
}