package ca.mcmaster.se2aa4.island.team44;

//Positon the drone to the top left corner
/* 
import javax.naming.ldap.ExtendedResponse;

import org.json.JSONObject;

enum State{
    ECHO_L,
    ECHO_R,
    ECHO_F,
    FLY,
    FACE_EAST,
    FACE_NORTH;
}

public class ExploreSpawn implements ExplorerPhase{

    private Translator translator = new Translator();


    private Drone d;
    private State state;
    public ExploreSpawn(Drone d){
        this.d =d;
        state= State.FACE_NORTH;
    }


//ECHO ALL DIRECTION
//IF FASING
    @Override
    public boolean getResponse(JSONObject response){

        if(state == State.FACE_NORTH){
            if(d.getDirection()== Compass.W) state = State.ECHO_F;
            state = State.FACE_NORTH;
        }

        return true;
    }

    @Override
    public String getDecision(){
/* 
        if(state == State.Echo_F){

        }
        else if(state == State.Echo_R){
            
        }else if(state == State.Echo_L){
            
        }if(state == State.WEST){
            if(d.getDirection() == Compass.N|| d.getDirection() == Compass.E ) d.left();
            else  d.right();
            return translator.heading(d.getDirection());
        }
        


    }
    
}
*/