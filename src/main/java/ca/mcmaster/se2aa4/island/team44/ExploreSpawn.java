package ca.mcmaster.se2aa4.island.team44;

//Positon the drone to the top left corner

import org.json.JSONObject;

enum State{
    ECHO_L,
    ECHO_R,
    ECHO_F,
    TURN1,
    TURN2,
    FLYCHECK,
    FLY,
    END; 
} 

public class ExploreSpawn implements ExplorerPhase{

    private Translator translator = new Translator();

    private Drone d;
    private State state;
    public ExploreSpawn(Drone d){
        this.d =d;
        state= State.ECHO_R;
    }
    int groundCount = 0;  
    boolean leftGround;
    boolean rightGround;
    boolean fwdGround;
    int flyCount;
   //private JSONObject echof;
    //private JSONObject echol;
    //private JSONObject echor;
//echof,echor,echol --> fly determines turn or continue echoing
//ECHO ALL DIRECTION
    @Override
    public boolean getResponse(JSONObject response){
        if (state == State.ECHO_R){
            if (translator.getFound(response).equals("GROUND")){
                groundCount+= 1;                
                leftGround= true;  
            }
            state= State.ECHO_L;
        }
        else if (state == State.ECHO_L){
            if (translator.getFound(response).equals("GROUND")){
                groundCount+= 1;
                rightGround = true; 
            }
            state= State.ECHO_F;
        }
        else if (state == State.ECHO_F){
            if(translator.getFound(response).equals("GROUND")){
                groundCount+= 1;
                fwdGround = true; 
            }else if(translator.getFound(response).equals("OUT_OF_RANGE")){
                flyCount = translator.getRange(response);
            }
            if(fwdGround){
                state= State.TURN1; 
            }else{
                state = State.FLY;
            }
        }
        else if (state == State.TURN1){
            state = State.TURN2;

        }
        else if (state == State.TURN2){
            state = State.FLYCHECK;

        }
        else if (state == State.FLYCHECK){
            flyCount= translator.getRange(response);
        }

        else if(state == State.FLY){
            if (flyCount <= 2){
                state= State.END; 
            }
            else{
                state = State.FLY;
            }

        }
        else if (state == State.END){
            return true; 
        }
        return false;


        
        /*if(state ==State.FLY){
            state=State.ECHO_F;
        }
        if(state == State.ECHO_F){
            if( (translator.getFound(response).equals("OUT_OF_RANGE") && translator.getRange(response)<2 ) || (translator.getFound(response).equals("GROUND") ) ){
                state = State.TURN_RIGHT;
            } else state = State.FLY;
            echof=response;
        
        }else if(state == State.TURN_RIGHT){
                state = State.ECHO_F;
        }
        else if (state == State.ECHO_R) {
            echor=response;
            state=State.ECHO_L;
        }
        else if(state==State.ECHO_L){
            echol=response;
            if(translator.getFound(echor).equals("GROUND")&&translator.getFound(echol).equals("GROUND"))
                state=State.TURN_RIGHT;
            else if(translator.getRange(echof)>2&&(translator.getRange(echol)>3&&translator.getRange(echor)>3)){
                if(translator.getRange(echor)<translator.getRange(echol))
                    state=State.TURN_RIGHT;
            }else if(translator.getRange(echof)<2&&(translator.getRange(echor)<3||translator.getRange(echol)<3)){
                if(translator.getRange(echor)>2)
                    state=State.TURNF_RIGHT;
                else
                    state=State.TURNF_LEFT;
                }
            }
        else if(state==State.TURNF_RIGHT||state==State.TURNF_RIGHT)
            return true;

        return false;
        */
    }

    @Override
    public String getDecision(){
        if (state == State.ECHO_R) return translator.echo(d.getDirection().right());
        else if(state == State.ECHO_L) return translator.echo(d.getDirection().left());
        else if (state == State.ECHO_F) return translator.echo(d.getDirection() );
        else if (state == State.TURN1){
            d.right();
            return translator.heading(d.getDirection());
        }else if (state == State.TURN2){
            d.right();
            return translator.heading(d.getDirection());
        }
        else if(state == State.FLYCHECK) return translator.echo(d.getDirection());
        else if (state == State. FLY){
            flyCount = flyCount -1;
            return translator.fly();
        }
        return "Default";
        

       /*  if(state == State.ECHO_F) return translator.echo(d.getDirection() );
        else if(state == State.ECHO_R) return translator.echo(d.getDirection().right());
        else if(state == State.ECHO_L) return translator.echo(d.getDirection().left());
        else if(state == State.FLY){
            d.fly();
            return translator.fly();
        }
        else if (state == State.TURN_RIGHT ||state == State.TURNF_RIGHT ){
            d.right();
            return translator.heading(d.getDirection());
        }
        else if(state == State.TURNF_LEFT||state == State.TURNF_LEFT){
             d.left();
            return translator.heading(d.getDirection());
        }

    } */
    
}
}