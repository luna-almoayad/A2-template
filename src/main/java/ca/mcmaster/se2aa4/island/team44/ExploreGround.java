package ca.mcmaster.se2aa4.island.team44;
import org.json.JSONObject;

enum State{
    R_ECHO,
    FLY,
    FINDGROUND,
    UTURN1,
    UTURN2,
    UTURN4;
}

public class ExploreGround implements ExplorerPhase{

    private State state = State.R_ECHO;
    protected JSONDataAdapter translate = new JSONDataParser();
    private int groundDistance;
    private Compass groundDirection;
    private Drone d;
    private Compass start;
    private int LCount =0; 
    private int RCount =0;
    private int turn=0; 

    private JSONObject echoRightResponse;
    private JSONObject echoLeftResponse;

    public ExploreGround(Drone d){
        this.d=d;
        this.start=d.getDirection();
    }

    @Override
    public String getDecision(){
        //Stop if Battery Low
        if(!d.sufficientBattery()){
            return d.stop();
        }else if(state == State.FINDGROUND )  
             return findGroundPhase();//Fly to Ground
        else if(state == State.FLY){
            return d.fly();
        }else if(state == State.R_ECHO){ 
            return echoPhase();
        }else if(state == State.UTURN1 || state == State.UTURN2 || state == State.UTURN4){  
            return uTurnPhase();
        }
        return d.stop();
    }

    public String findGroundPhase(){
        if(groundDirection != d.getDirection()){
            return d.setDirection(groundDirection); 
            }
            groundDistance --;
            return d.fly();
    }


    public String uTurnPhase(){
        if(state == State.UTURN1) {
            start=d.getDirection();
            return d.fly();
        }else if(state == State.UTURN2){
            turn++;
            if(groundDirection==start.right()){  
                return d.right();
            }else{
                return d.left();
            }
        }else if (state == State.UTURN4) {
            groundDistance-=d.getSafeRange();
            // we repeat this many times can we maybe fo helper method or an opposite dir?
            if(groundDirection==start.right()){ 
               return d.left();
            }else{
                return d.right();
            }
        }
        return d.stop();
    }

    public String echoPhase(){
        if( this.state == State.R_ECHO ) {
            groundDirection = d.getDirection().right();
            return d.echo("R");
        }
        return d.stop();
    }


    @Override
    public boolean getResponse(JSONObject response)
    {   
        //Deduct Cost
        d.deductCost(translate.getCost(response));

        if(state == State.FINDGROUND ){
           if(groundDistance ==0) return true;
        }else if(state == State.FLY){
            state = State.R_ECHO;
        }else if(state == State.R_ECHO){ 
            return getEchoResponse(response);
        }else if(state == State.UTURN1 || state == State.UTURN2 || state == State.UTURN4 ){  
            return uTurnGetResponse();
        }
        return false;
         
    }


    public boolean getEchoResponse(JSONObject response){
        if(state == State.R_ECHO){
            echoRightResponse = response;
            if(d.isGround(response)){
                state = State.UTURN1;
                groundDistance = translate.getRange(response);
            }else{
                state = State.FLY;
            }

        }
        return false;
    }


//Change state of Utunr
    public boolean uTurnGetResponse(){
        if( state == State.UTURN1){ 
            state = State.UTURN2;
        }else if(state== State.UTURN2){
            if (turn!=2){ state =State.UTURN2 ;}
            else{ state = State.UTURN4;}   
        }else if(state == State.UTURN4){  
            state = State.FINDGROUND;
        }
        return false;
    }
}
