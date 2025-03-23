package ca.mcmaster.se2aa4.island.team44;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

enum Spins{
    SCAN,
    FLY,
    ECHO_F,
    TURN_RIGHT,
    TURN_LEFT,
    ECHO_R,
    END;
}


public class ExploreSpin implements ExplorerPhase{
    private Translator translator = new Translator();
    private int distance=0;
    private final Logger logger = LogManager.getLogger();
    private boolean finalturn=false;
    private int right_turns =0;
    private Drone d;
    private Spins state;

    public ExploreSpin(Drone d){
        state=Spins.SCAN;
        this.d=d;
    }

    @Override
    public boolean getResponse(JSONObject response){
        if(state==Spins.SCAN) state=Spins.ECHO_F;
        else if(state==Spins.ECHO_F){
            if(translator.getFound(response).equals("OUT_OF_RANGE")&&translator.getRange(response)<2)
                state=Spins.END;
            else
                state=Spins.FLY;
        }
        else if(d.getDirection()==Compass.W)  return true;
        else if(state==Spins.ECHO_R){
            if(translator.getFound(response).equals("OUT_OF_RANGE")&&translator.getRange(response)<10)
                state=Spins.TURN_LEFT;
            else
                state=Spins.TURN_RIGHT;
        }else if(state==Spins.FLY) state=Spins.ECHO_R;
        else if(state==Spins.END) return true; 
        else state=Spins.ECHO_R;
        return false;
    }

    public String getDecision(){
        if(state==Spins.ECHO_R) return translator.echo(d.getDirection().right());
        else if(state==Spins.TURN_RIGHT){
            d.right();
            return translator.heading(d.getDirection());
        }else if(state==Spins.TURN_LEFT){
            d.left();
            return translator.heading(d.getDirection());
        }else if(state==Spins.ECHO_F) return translator.echo(d.getDirection());
        else if(state==Spins.END) return translator.stop();
        else if(state==Spins.SCAN) return translator.scan();
        else return translator.fly();

    }
}