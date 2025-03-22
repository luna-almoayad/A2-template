package ca.mcmaster.se2aa4.island.team44;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

enum Spins{
    FLY,
    TURN_RIGHT,
    TURN_LEFT,
    ECHO_R;
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
        state=Spins.FLY;
        this.d=d;
    }

    @Override
    public boolean getResponse(JSONObject response){
        if(d.getDirection()==Compass.W)
            return true;
        if(state==Spins.ECHO_R){
            if(translator.getFound(response).equals("OUT_OF_RANGE")&&translator.getRange(response)<10)
                state=Spins.TURN_LEFT;
            else
                state=Spins.TURN_RIGHT;
        }else if(state==Spins.FLY) state=Spins.ECHO_R;
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
        }else return translator.fly();

    }
}