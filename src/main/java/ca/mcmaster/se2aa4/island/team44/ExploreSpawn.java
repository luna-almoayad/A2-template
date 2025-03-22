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
    private int right_turns =0;
    private Drone d;
    private States state;
    public ExploreSpawn(Drone d){
        this.d =d;
        if(d.getDirection()!=Compass.W)
            state= States.TURN_LEFT;
        else
            state= States.ECHO_EDGE; //echor, echol, echof
    }

    @Override
    public boolean getResponse(JSONObject response){
        logger.info("ayo "+state);
            if(state==States.ECHO_EDGE){ //flies to edge
                if((translator.getRange(response) > 3&&translator.getFound(response).equals("OUT_OF_RANGE"))||translator.getFound(response).equals("GROUND")){ //if range in front of you is greater than 3, travel there
                    distance = translator.getRange(response)-3;
                    state = States.FLY;
                } else {
                    state = States.TURN_RIGHT;
                }
            }
            else if(state==States.FLY) {
                logger.info("mees"+distance);
                if(distance <=0) {
                    state = States.ECHO_EDGE;
                } else {
                    distance--;
                }
            }
            else if(state==States.TURN_RIGHT) {
                right_turns++;
                state = States.ECHO_CORNER;
            }
            else if(state==States.ECHO_CORNER) {
                if(right_turns==2) {
                    d.setLocation(0,0);
                    return true;
                }
                if(translator.getRange(response) > 3){ //if range in front of you is greater than 3, travel there
                    distance = translator.getRange(response)-3;
                    state = States.FLY;
                } else {
                    state = States.TURN_RIGHT;
                }
                logger.info("dooch"+finalturn);
            }
            else if(state==States.TURN_LEFT) {
                if(d.getDirection() == Compass.W) {
                    state = States.ECHO_EDGE;
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
            if(state==States.ECHO_EDGE || state==States.ECHO_CORNER) {
                return translator.echo(d.getDirection());
            }
            else if(state==States.FLY) {
                d.fly();
                return translator.fly();
            }
            else if(state==States.TURN_RIGHT) {
                d.right();
                return translator.heading(d.getDirection());
            }
            else if(state==States.TURN_LEFT) {
                d.left();
                return translator.heading(d.getDirection());
            }
            else {
                return "No";
            }
    }
}