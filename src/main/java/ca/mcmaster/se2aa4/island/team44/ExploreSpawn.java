package ca.mcmaster.se2aa4.island.team44;

//Positon the drone to the top left corner

import org.json.JSONObject;

enum States{
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
    private States state;
    public ExploreSpawn(Drone d){
        this.d =d;
        state= States.ECHO_R;
    }
    int groundCount = 0;  
    boolean leftGround;
    boolean rightGround;
    boolean fwdGround;
    int flyCount;
    boolean or;
   //private JSONObject echof;
    //private JSONObject echol;
    //private JSONObject echor;
//echof,echor,echol --> fly determines turn or continue echoing
//ECHO ALL DIRECTION
    @Override
    public boolean getResponse(JSONObject response){
        if (state == States.ECHO_R){
            if (translator.getFound(response).equals("GROUND")){
                groundCount+= 1;                
                leftGround= true;  
            }
            state= States.ECHO_L;
        }
        else if (state == States.ECHO_L){
            if (translator.getFound(response).equals("GROUND")){
                groundCount+= 1;
                rightGround = true; 
            }
            state= States.ECHO_F;
        }
        else if (state == States.ECHO_F){
            if(translator.getFound(response).equals("GROUND")){
                groundCount+= 1;
                fwdGround = true;

                if(fwdGround && !rightGround && !leftGround){
                    return true;
                }
                else if (fwdGround){
                    state= States.TURN1; 
            }
            }else if(translator.getFound(response).equals("OUT_OF_RANGE")){
                flyCount = translator.getRange(response);
                or= true; 
                }if (or && !rightGround && !leftGround){
                    return true;
            }else{
                state = States.FLY;
            }
        }else if (state == States.TURN1){
            state = States.TURN2;

        }
        else if (state == States.TURN2){
            state = States.FLYCHECK;

        }
        else if (state == States.FLYCHECK){
            flyCount= translator.getRange(response);
        }

        else if(state == States.FLY){
            if (flyCount == 3){
                return true; 
            }
            else{
                state = States.FLY;
            }

        }
        else if (state == States.END){
            return true; 
        }
        return false;


        
        /*if(state ==States.FLY){
            state=States.ECHO_F;
        }
        if(state == States.ECHO_F){
            if( (translator.getFound(response).equals("OUT_OF_RANGE") && translator.getRange(response)<2 ) || (translator.getFound(response).equals("GROUND") ) ){
                state = States.TURN_RIGHT;
            } else state = States.FLY;
            echof=response;
        
        }else if(state == States.TURN_RIGHT){
                state = States.ECHO_F;
        }
        else if (state == States.ECHO_R) {
            echor=response;
            state=States.ECHO_L;
        }
        else if(state==States.ECHO_L){
            echol=response;
            if(translator.getFound(echor).equals("GROUND")&&translator.getFound(echol).equals("GROUND"))
                state=States.TURN_RIGHT;
            else if(translator.getRange(echof)>2&&(translator.getRange(echol)>3&&translator.getRange(echor)>3)){
                if(translator.getRange(echor)<translator.getRange(echol))
                    state=States.TURN_RIGHT;
            }else if(translator.getRange(echof)<2&&(translator.getRange(echor)<3||translator.getRange(echol)<3)){
                if(translator.getRange(echor)>2)
                    state=States.TURNF_RIGHT;
                else
                    state=States.TURNF_LEFT;
                }
            }
        else if(state==States.TURNF_RIGHT||state==States.TURNF_RIGHT)
            return true;

        return false;
        */
    }

    @Override
    public String getDecision(){
        if (state == States.ECHO_R) return translator.echo(d.getDirection().right());
        else if(state == States.ECHO_L) return translator.echo(d.getDirection().left());
        else if (state == States.ECHO_F) return translator.echo(d.getDirection() );
        else if (state == States.TURN1){
            d.right();
            return translator.heading(d.getDirection());
        }else if (state == States.TURN2){
            d.right();
            return translator.heading(d.getDirection());
        }
        else if(state == States.FLYCHECK) return translator.echo(d.getDirection());
        else if (state == States. FLY){
            flyCount = flyCount -1;
            return translator.fly();
        }
        return "Default";
        

       /*  if(state == States.ECHO_F) return translator.echo(d.getDirection() );
        else if(state == States.ECHO_R) return translator.echo(d.getDirection().right());
        else if(state == States.ECHO_L) return translator.echo(d.getDirection().left());
        else if(state == States.FLY){
            d.fly();
            return translator.fly();
        }
        else if (state == States.TURN_RIGHT ||state == States.TURNF_RIGHT ){
            d.right();
            return translator.heading(d.getDirection());
        }
        else if(state == States.TURNF_LEFT||state == States.TURNF_LEFT){
             d.left();
            return translator.heading(d.getDirection());
        }

    } */
    
}
}