package ca.mcmaster.se2aa4.island.team44;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;




enum States{
    ECHO_L,
    ECHO_R,
    ECHO_F,
    FLY,
    TURN_RIGHT,
    TURN_LEFT,
    END;
}




public class ExploreSpawn implements ExplorerPhase{




    private Translator translator = new Translator();
    int distance=0;
    private final Logger logger = LogManager.getLogger();




    private Drone d;
    private States state;
    public ExploreSpawn(Drone d){
        this.d =d;
        state= States.ECHO_F; //echor, echol, echof
    }


    private JSONObject echof;
    private JSONObject echol;
    private JSONObject echor;
//echof,echor,echol --> fly determines turn or continue echoing
//ECHO ALL DIRECTION
    @Override
    public boolean getResponse(JSONObject response){
        d.deductCost(translator.getCost(response));
        logger.info("**Battery" + d.checkBattery());


        if(this.state == States.FLY){
            distance--;
            if(distance<=2)
                this.state=States.ECHO_R;//go to echor then echol to see which is less
            //else stay in fly until u echoR
            else this.state = States.FLY;
        }
        //goes f, r, l
        else if(this.state == States.ECHO_F || state == States.ECHO_R ||state == States.ECHO_L){
            return getEchoResponse(response);
        }else if(this.state==States.TURN_LEFT){
            this.state=States.ECHO_F;
        }else if(this.state == States.TURN_RIGHT){
            state = States.ECHO_F;
        }


        return false;
    }


    public Boolean getEchoResponse(JSONObject response){
        if(state == States.ECHO_F){
            echof=response;
            if (translator.getFound(echof).equals("OUT_OF_RANGE") && translator.getRange(echof)==0 ){
                state = States.END;
            }else if( (translator.getFound(echof).equals("OUT_OF_RANGE") && translator.getRange(echof)<2 ) || (translator.getFound(echof).equals("GROUND") ) ){
                state = States.TURN_RIGHT; //if the front is out of range <2 or ground, turn right
            } else {
                distance = translator.getRange(response);
                state = States.FLY; //else fly in that direction until out of range
            }
        }else if (state == States.ECHO_R) {
            echor=response;
            state=States.ECHO_L;
       
        }else if(state==States.ECHO_L){


            echol=response;
            if(translator.getFound(echor).equals("GROUND")&&translator.getFound(echol).equals("GROUND"))
                state=States.FLY; //ensures no ground in forward direction
            else if((translator.getRange(echol)>3&&translator.getRange(echor)>3)){ //translator.getRange(echof)<2&&
                if(translator.getRange(echor)<translator.getRange(echol)){
                    state=States.TURN_RIGHT;
                }else {
                    state=States.TURN_LEFT;
                }
            }else
                return true;
               
        }
        return false;
    }




    @Override
    public String getDecision(){
        if(!d.sufficientBattery()){
        logger.info("**Low Battery: Returning to Base");
        return translator.stop();
        }


        if(state == States.ECHO_F)  
            return translator.echo(d.getDirection() );


        else if(state == States.ECHO_R)
            return translator.echo(d.getDirection().right());
        else if(state == States.ECHO_L)
             return translator.echo(d.getDirection().left());
        else if(state == States.FLY){
            d.fly();
            return translator.fly();
        }
        else if (state == States.TURN_RIGHT){
            d.right();
            return translator.heading(d.getDirection());
        }
        else if(state == States.TURN_LEFT){
            d.left();
            return translator.heading(d.getDirection());
        }else if(state == States.END){
            return translator.stop();
        }
        d.fly();
        return translator.fly();
    }
   
}

