package ca.mcmaster.se2aa4.island.team44.explorer;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team44.drones.Drone;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataAdapter;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataParser;
import ca.mcmaster.se2aa4.island.team44.navigation.Compass;

// Enum to represent the different states in the exploration spin
enum Spins {
    FLY,        // Fly state
    ECHO_F,     // Echo forward state
    TURN_RIGHT, // Turn right state
    TURN_LEFT,  // Turn left state
    ECHO_R,     // Echo right state
    END;        // End state
}

public class ExploreSpin implements ExplorerPhase {
    // JSON data adapter for parsing JSON data
    private JSONDataAdapter translator = new JSONDataParser();
    
    // Drone instance
    private Drone d;
    
    // Current state in the exploration spin
    private Spins state;

    // Constructor to initialize the ExploreSpin with a drone
    public ExploreSpin(Drone d) {
        state = Spins.ECHO_F;
        this.d = d;
    }

    @Override
    public boolean getResponse(JSONObject response) {
        if (state == Spins.ECHO_F) {
            if (!d.isGround(response) && translator.getRange(response) < 2) {
                state = Spins.END;
            } else {
                state = Spins.FLY;
            }
        } else if (d.getDirection() == Compass.W) { 
            return true;
        } else if (state == Spins.ECHO_R) {
            if (!d.isGround(response) && !d.ifSafeRange(translator.getRange(response))) {
                // It shouldn't ever echo forward, should only echo right, make sure it's at top right
                state = Spins.TURN_LEFT;
            } else {
                state = Spins.TURN_RIGHT;
            }
        } else if (state == Spins.FLY) {
            state = Spins.ECHO_R;
        } else if (state == Spins.END) {
            return true; 
        } else {
            state = Spins.ECHO_R;
        }
        return false;
    }

    public String getDecision() {
        if (state == Spins.ECHO_R) { 
            return d.echo("R");
        } else if (state == Spins.TURN_RIGHT) {
            return d.right();
        } else if (state == Spins.TURN_LEFT) {
            return d.left();
        } else if (state == Spins.ECHO_F) {
            return d.echo("F");
        } else if (state == Spins.END) { 
            return d.stop();
        } else {
            return d.fly();
        }
    }
}