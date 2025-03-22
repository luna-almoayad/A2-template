package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

enum State{
    R_ECHO,
    F_ECHO,
    FLY,
    FINDGROUND,
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
        }else if(this.state == State.FINDGROUND )  
             return this.findGroundPhase();//Fly to Ground
        else if(this.state == State.FLY){
                d.fly();
                return translate.fly();

        }else if(this.state == State.F_ECHO || this.state == State.R_ECHO) 
            return this.echoPhase();
        else if(this.state == State.UTURN1 || this.state == State.UTURN2 || this.state == State.UTURN3 || this.state == State.UTURN4 )  
            return this.uTurnPhase();
        
        return translate.stop();
    }


    public String findGroundPhase(){
        if(this.groundDirection != d.getDirection()){
                d.setDirection(this.groundDirection);
                return translate.heading(this.groundDirection); 
            }
            this.groundDistance = this.groundDistance - 1;
            d.fly();
            return translate.fly();
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

        if(this.state == State.FINDGROUND ){
           if(this.groundDistance ==0) return true;

        }else if(this.state == State.FLY) this.state = State.R_ECHO;

        else if(this.state == State.F_ECHO || this.state == State.R_ECHO) 
            return this.getEchoResponse(response);
        else if(this.state == State.UTURN1 || this.state == State.UTURN2 || this.state == State.UTURN3 || this.state == State.UTURN4 )  
            return this.uTurnGetResponse();
        return false;
         
    }


    public boolean getEchoResponse(JSONObject response){
        if(this.state == State.R_ECHO){
            this.echoRightResponse = response;
            if(translate.getFound(response).equals("GROUND")){
                if(d.getLocation().getXCoord()<3)
                    state=State.FINDGROUND;
                else
                    this.state = State.UTURN1;
                this.groundDistance = translate.getRange(response);

            }else this.state = State.F_ECHO;

        }else if(this.state == State.F_ECHO ) {
            if(translate.getFound(response).equals("GROUND")){
                this.state = State.FINDGROUND;
                this.groundDistance = translate.getRange(response);
            }
            else   this.state = State.FLY;
        }
        return false;
    }

//Change state of Uturn
    public boolean uTurnGetResponse(){

            if( this.state == State.UTURN1) this.state = State.UTURN2;
            else if(this.state== State.UTURN2)   this.state = State.UTURN3;
            else if(this.state == State.UTURN3)  this.state = State.UTURN4;
            else if(this.state == State.UTURN4)  this.state = State.FINDGROUND;

            return false;
    }
        
}