package ca.mcmaster.se2aa4.island.team44.explorer;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team44.drones.Drone;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataAdapter;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataParser;

// Enum to represent the different states in the exploration spawn
enum States {
    ECHO_EDGE,  // Echo edge state
    FLY,        // Fly state
    TURN_RIGHT, // Turn right state
    TURN_LEFT,  // Turn left state
    ECHO_R;     // Echo right state
}

public class ExploreSpawn implements ExplorerPhase {
    // JSON data adapter for parsing JSON data
    private JSONDataAdapter translator = new JSONDataParser();
    
    // Distance to travel
    private int distance = 0;
    
    // Number of turns made
    private int turns = 0;
    
    // Drone instance
    private Drone d;
    
    // Current state in the exploration spawn
    private States state;
    
    // Constructor to initialize the ExploreSpawn with a drone
    public ExploreSpawn(Drone d) {
        this.d = d;
        state = States.ECHO_EDGE; // Initial state is echo edge
    }

    @Override
    public boolean getResponse(JSONObject response) {
        if (state == States.ECHO_EDGE) { // Flies to edge
            if ((d.ifSafeRange(translator.getRange(response)) && !d.isGround(response)) || d.isGround(response)) { // If range in front is greater than 3, travel there
                distance = translator.getRange(response) - 5;
                state = States.FLY;
            } else {
                state = States.ECHO_R;
            }
        } else if (state == States.FLY) {
            if (distance <= 0) {
                state = States.ECHO_EDGE;
            } else {
                distance--;
            }
        } else if (state == States.TURN_RIGHT || state == States.TURN_LEFT) {
            turns++;
            state = States.ECHO_EDGE;
            if (turns == 2) {
                return true;
            }
        } else if (state == States.ECHO_R) {
            if (!d.ifSafeRange(translator.getRange(response)) && !d.isGround(response)) {
                state = States.TURN_LEFT;
            } else {
                state = States.TURN_RIGHT;
                distance = translator.getRange(response) - d.getSafeRange();
            }
        }
        return false;
    }

    @Override
    public String getDecision() {
        if (!d.sufficientBattery()) {
            return d.stop();
        }
        if (state == States.ECHO_EDGE) {
            return d.echo("F");
        } else if (state == States.FLY) {
            return d.fly();
        } else if (state == States.ECHO_R) {
            return d.echo("R");
        } else if (state == States.TURN_RIGHT) {
            return d.right();
        } else if (state == States.TURN_LEFT) {
            return d.left();
        } else {
            return null;
        }
    }
}
