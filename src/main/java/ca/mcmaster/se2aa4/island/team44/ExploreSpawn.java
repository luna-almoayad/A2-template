package ca.mcmaster.se2aa4.island.team44;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

enum States{
    ECHO_EDGE,
    ECHO_CORNER,
    FLY,
    TURN_RIGHT,
    TURN_LEFT;
}

public class ExploreSpawn implements ExplorerPhase{
    private Translator translator = new Translator();
    private int distance=0;
    private final Logger logger = LogManager.getLogger();
    private boolean finalturn=false;
    private Drone d;
    private States state;
    public ExploreSpawn(Drone d){
        this.d =d;
        if(d.getDirection()!=Compass.W)
            state= States.TURN_LEFT;
        else
            state= States.ECHO_EDGE; //echor, echol, echof
    }
//set startdir
//echof,echor,echol --> fly determines turn or continue echoing
//ECHO ALL DIRECTION
    @Override
    public boolean getResponse(JSONObject response){
        logger.info("mees"+state);
        switch(state){
            case ECHO_EDGE -> { //flies to edge
                if(translator.getRange(response) >= 3){ //if range in front of you is greater than 3, travel there
                    distance = translator.getRange(response);
                    state = States.FLY;
                } else {
                    state = States.TURN_RIGHT;
                }
            }
            case FLY -> {
                if(distance == 2) {
                    state = States.ECHO_EDGE;
                } else {
                    distance--;
                }
            }
            case TURN_RIGHT -> {
                if(finalturn) {
                    d.setLocation(0,0);
                    return true;
                }
                state = States.ECHO_CORNER;
            }
            case ECHO_CORNER -> {
                if(translator.getRange(response) >= 3){ //if range in front of you is greater than 3, travel there
                    distance = translator.getRange(response);
                    state = States.FLY;
                } else {
                    state = States.TURN_RIGHT;
                    finalturn = true;
                }
            }
            case TURN_LEFT -> {
                if(d.getDirection() == Compass.W) {
                    state = States.ECHO_EDGE;
                }
            }
            default -> {
                return false;
            }
        }
        return false;
    }

    @Override
    public String getDecision(){
        if(!d.sufficientBattery()){
            logger.info("**Low Battery: Returning to Base");
            return translator.stop();
        }
        switch(state){
            case ECHO_EDGE -> {
                return translator.echo(d.getDirection());
            }
            case FLY -> {
                d.fly();
                return translator.fly();
            }
            case TURN_RIGHT -> {
                d.right();
                return translator.heading(d.getDirection());
            }
            case TURN_LEFT -> {
                d.left();
                return translator.heading(d.getDirection());
            }
            case ECHO_CORNER -> {
                return translator.echo(d.getDirection());
            }
            default -> {
                return "No";
            }
        }
    }
}