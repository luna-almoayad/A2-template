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
    TURN_L_1,
    TURN_L_2,
    TURN_R_1,
    TURN_R_2,
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

    private JSONObject echoLeftResponse;
    private JSONObject echoRightResponse;

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
    }

    if(this.state == State.FOUNDGROUND ){

        if(this.groundDirection != d.getDirection()){
                d.setDirection(this.groundDirection);
                return translate.heading(this.groundDirection); 
            }
            this.groundDistance = this.groundDistance - 1;
            d.fly();
            return translate.fly();
           
    }else if(this.state == State.FLY){
            d.fly();
            return translate.fly();

    }else if(this.state == State.F_ECHO || this.state == State.L_ECHO || this.state == State.R_ECHO){
        return this.echoPhase();
    }else if(this.state == State.UTURN1 || this.state == State.UTURN2 || this.state == State.UTURN3 || this.state == State.UTURN4 ){
        return this.uTurnPhase();
    }else if(this.state == State.TURN_L_1 || this.state == State.TURN_L_2 || this.state == State.TURN_R_1 ||this.state == State.TURN_R_2 ){
        return this.turnPhase();
    }
    return translate.stop();
    }


    public String turnPhase(){
        if(this.state == State.TURN_L_1) {
                d.left();
                return translate.heading(d.getDirection());
        }
        else if(this.state == State.TURN_L_2){
                d.left();
                return translate.heading(d.getDirection());
        }else if(this.state == State.TURN_R_1){
                d.right();
                return translate.heading(d.getDirection());
        }else if(this.state == State.TURN_R_2){
                d.right();
                return translate.heading(d.getDirection());
        }
        return translate.stop();
    }

    public String uTurnPhase(){
        if(this.state == State.UTURN1) {
            start=d.getDirection();
            d.fly();
            return translate.fly();

        }else if(this.state == State.UTURN2){
            if(groundDirection==start.right())  d.right();
            else d.left();

            return translate.heading(d.getDirection());

        }else if(this.state == State.UTURN3) {

            if(this.groundDirection==start.right()) d.right();
            else d.left();

            return translate.heading(d.getDirection());
        }else if (this.state == State.UTURN4) {
            this.groundDistance-=3;

            if(this.groundDirection==start.right())  d.left();
            else  d.right();

            return translate.heading(d.getDirection());
        }
        return translate.stop();
    }

    public String echoPhase(){
        if(this.state == State.F_ECHO ){
            this.groundDirection = d.getDirection();
            return translate.echo(d.getDirection());

        }else if( this.state == State.L_ECHO){

            this.groundDirection = d.getDirection().left();
            return translate.echo(d.getDirection().left());

        }else if( this.state == State.R_ECHO ) {
            this.groundDirection = d.getDirection().right();
            return translate.echo(d.getDirection().right());
        }
        return translate.stop();
    }


    @Override
    public boolean getResponse(JSONObject response)
    {   
        //Deduct Cost
        d.deductCost(translate.getCost(response));
        logger.info("**Battery" + d.checkBattery());

        if(this.state == State.FOUNDGROUND ){
           if(this.groundDistance ==0) return true;

        }else if(this.state == State.FLY) this.state = State.R_ECHO;

        else if(this.state == State.F_ECHO || this.state == State.L_ECHO || this.state == State.R_ECHO) return this.getEchoResponse(response);
        else if(this.state == State.UTURN1 || this.state == State.UTURN2 || this.state == State.UTURN3 || this.state == State.UTURN4 )  return this.uTurnGetResponse();
        else if(this.state == State.TURN_L_1 || this.state == State.TURN_L_2 || this.state == State.TURN_R_1 ||this.state == State.TURN_R_2 )  return this.turnGetResponse();

        return false;
         
    }


    public boolean getEchoResponse(JSONObject response){

        if(this.state == State.R_ECHO){

            this.echoRightResponse = response;
            if(translate.getFound(response).equals("GROUND")){
                this.state = State.UTURN1;
                this.groundDistance = translate.getRange(response);

            }else this.state = State.L_ECHO;

        } else if( this.state ==State.L_ECHO ) {

            this.echoLeftResponse = response;
            if(translate.getFound(response).equals("GROUND")){
                this.state = State.UTURN1;
                this.groundDistance = translate.getRange(response);
                
            } else this.state = State.F_ECHO;
        }else if(this.state == State.F_ECHO ) {

            if(translate.getFound(response).equals("GROUND")){
                this.state = State.FOUNDGROUND;
                this.groundDistance = translate.getRange(response);

            }else if(translate.getFound(response).equals("OUT_OF_RANGE") && translate.getRange(response)<3 ){            
                    if( translate.getRange(this.echoLeftResponse) > translate.getRange(this.echoRightResponse) ) this.state = State.TURN_L_1;
                    else this.state = State.TURN_R_1; 
            }else   this.state = State.FLY;
         }
        return false;
    }
//Change state of turn
    public boolean turnGetResponse(){
        if(this.state == State.TURN_L_1 )   this.state = State.TURN_L_2;
        else if(this.state == State.TURN_L_2)  this.state = State.F_ECHO;
        else if(this.state ==  State.TURN_R_1) this.state = State.TURN_R_2;
        else if(this.state ==  State.TURN_R_2 ) this.state = State.F_ECHO;
        
        return false;
    }
//Change state of Utunr
    public boolean uTurnGetResponse(){

            if( this.state == State.UTURN1) this.state = State.UTURN2;
            else if(this.state== State.UTURN2)   this.state = State.UTURN3;
            else if(this.state == State.UTURN3)  this.state = State.UTURN4;
            else if(this.state == State.UTURN4)  this.state = State.FOUNDGROUND;

            return false;
    }
        
}