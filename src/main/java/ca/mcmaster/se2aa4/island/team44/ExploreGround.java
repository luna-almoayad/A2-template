package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

enum State{
    L_ECHO,
    R_ECHO,
    F_ECHO,
    FLY,
    FINDGROUND,
    TURN_L,
    TURN_R,
    UTURN1,
    UTURN2,
    UTURN3,
    UTURN4;
}


public class ExploreGround implements ExplorerPhase{

    private State state = State.R_ECHO;
    protected JSONTranslator translate = new Translator();
    private int groundDistance;
    private Compass groundDirection;
    private final Logger logger = LogManager.getLogger();
    private Drone d;
    private Compass start;
    private int LCount =0; 
    private int RCount =0; 

    private JSONObject echoRightResponse;
    private JSONObject echoLeftResponse;

    public ExploreGround(Drone d){
        this.d=d;
        this.start=d.getDirection();
    }

    @Override
    public String getDecision(){
        logger.info("***ExploreGround State: "+ state);

        //Stop if Battery Low
        if(!d.sufficientBattery()){
            logger.info("**Low Battery: Returning to Base");
            return translate.stop();
        }else if(state == State.FINDGROUND )  
             return findGroundPhase();//Fly to Ground
        else if(state == State.FLY){
            return d.fly();
        }else if(state == State.F_ECHO || state == State.L_ECHO || state == State.R_ECHO){ 
            return echoPhase();
        }else if(state == State.UTURN1 || state == State.UTURN2 || state == State.UTURN3 || state == State.UTURN4){  
            return uTurnPhase();
        }else if(state == State.TURN_L|| state == State.TURN_R){  
            return turnPhase();
        }
        return translate.stop();
    }

    public String findGroundPhase(){
        if(groundDirection != d.getDirection()){
            return d.setDirection(groundDirection); 
            }
            groundDistance --;
            return d.fly();
    }

    public String turnPhase(){
        if(state == State.TURN_L) {
                LCount ++;
                return d.left();
        }else{
                RCount ++;
                return d.right();
        }
    }

    public String uTurnPhase(){
        if(state == State.UTURN1) {
            start=d.getDirection();
            return d.fly();
        }else if(state == State.UTURN2){
            if(groundDirection==start.right()){  
                return d.right();
            }else{
                return d.left();
            }

        }else if(state == State.UTURN3) {

            if(groundDirection==start.right()){
                return d.right();
            }else{ 
                return d.left();
            }
        }else if (state == State.UTURN4) {
            groundDistance-=3;
            // we repeat this many times can we maybe fo helper method or an opposite dir?
            if(groundDirection==start.right()){ 
               return d.left();
            }else{
                return d.right();
            }
        }
        return translate.stop();
    }

    public String echoPhase(){
        if(state == State.F_ECHO ){
            groundDirection = d.getDirection();
            return d.echo("F");

        }else if(state == State.L_ECHO){
            groundDirection = d.getDirection().left();
            return d.echo("L");

        }else if( this.state == State.R_ECHO ) {
            groundDirection = d.getDirection().right();
            return d.echo("R");
        }
        return translate.stop();
    }


    @Override
    public boolean getResponse(JSONObject response)
    {   
        //Deduct Cost
        d.deductCost(translate.getCost(response));
        logger.info("**Battery" + d.checkBattery());

        if(state == State.FINDGROUND ){
           if(groundDistance ==0) return true;

        }else if(state == State.FLY){
            state = State.R_ECHO;

        }else if(state == State.F_ECHO || state == State.L_ECHO || state == State.R_ECHO){ 
            return getEchoResponse(response);
        }
        else if(state == State.UTURN1 || state == State.UTURN2 || state == State.UTURN3 || state == State.UTURN4 ){  
            return uTurnGetResponse();
        }else if(state == State.TURN_L || this.state == State.TURN_R){ 
            return turnGetResponse();
        }
        return false;
         
    }


    public boolean getEchoResponse(JSONObject response){
        if(state == State.R_ECHO){

            echoRightResponse = response;
            if(translate.getFound(response).equals("GROUND")){
                state = State.UTURN1;
                groundDistance = translate.getRange(response);

            }else{
                state = State.L_ECHO;
            }

        } else if(state ==State.L_ECHO ) {

            echoLeftResponse = response;
            if(d.isGround(response)){
                state = State.UTURN1;
                groundDistance = translate.getRange(response);
                
            } else{
                state = State.F_ECHO;
            }
        }else if(state == State.F_ECHO ) {

            if(d.isGround(response)){
                state = State.FINDGROUND;
                groundDistance = translate.getRange(response);

            }else if(d.isGround(response) && translate.getRange(response)<3 ){            
                    if(translate.getRange(this.echoLeftResponse) > translate.getRange(this.echoRightResponse) ){ 
                        state = State.TURN_L;
                    }else{
                        state = State.TURN_R; 
                    }
            }else{ 
                state = State.FLY;
            }
         }
        return false;
    }

//Change state of turn
    public boolean turnGetResponse(){
        if(state == State.TURN_L ){
            if (LCount == 2){
                LCount =0;
                state = State.F_ECHO;
            }else if(0< LCount && LCount <2){
                state = State.TURN_L;
            }
   
        }else if(state ==  State.TURN_R){
            if (RCount == 2){
                RCount =0;
                state = State.F_ECHO;
            }else if(0< RCount && RCount <2){
                state = State.TURN_R;
            }
        }
        return false;
    }
//Change state of Utunr
    public boolean uTurnGetResponse(){
        if( state == State.UTURN1){ 
            state = State.UTURN2;
        }else if(state== State.UTURN2){   
            state = State.UTURN3;
        }else if(state == State.UTURN3){  
            state = State.UTURN4;
        }else if(state == State.UTURN4){  
            state = State.FINDGROUND;
        }
        return false;
    }
}