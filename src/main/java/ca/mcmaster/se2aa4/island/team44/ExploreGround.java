package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONObject;


public class ExploreGround implements ExplorerPhase{


    private State state = State.FLY;

    protected Translator translate = new Translator();

    private int groundDistance;
    private Compass groundDirection;


    //TAKE THIS OUT L

    int t = 20;


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


    @Override
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

            d.fly();
            return translate.fly();
            }

        case State.FLY -> {
            state = State.F_ECHO;
            d.fly();
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
            state = State.E;
            return translate.fly();
            }
        case State.E ->{
            state = State.STOP;
            t--;
            if(t ==0) return translate.stop();
            return translate.scan();
        }
        default -> {
            return "Default";
            }

        
    }
 

    }
         
}
