package ca.mcmaster.se2aa4.island.team44.explorer;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team44.drones.Drone;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataAdapter;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataParser;
import ca.mcmaster.se2aa4.island.team44.navigation.Compass;

// Enum to represent the different states in the exploration ground phase
enum State {
    R_ECHO,     // Echo right state
    FLY,        // Fly state
    FINDGROUND, // Find ground state
    UTURN1,     // U-turn phase 1
    UTURN2,     // U-turn phase 2
    UTURN4;     // U-turn phase 4
}

public class ExploreGround implements ExplorerPhase {

    // Initial state is echo right
    private State state = State.R_ECHO;
    
    // JSON data adapter for parsing JSON data
    protected JSONDataAdapter translate = new JSONDataParser();
    
    // Distance to the ground
    private int groundDistance;
    
    // Direction to the ground
    private Compass groundDirection;
    
    // Drone instance
    private Drone d;
    
    // Starting direction
    private Compass start;
    
    // Counters for left and right turns
    private int LCount = 0;
    private int RCount = 0;
    
    // Turn counter
    private int turn = 0;

    // JSON responses for echo right and left
    private JSONObject echoRightResponse;
    private JSONObject echoLeftResponse;

    // Constructor to initialize the ExploreGround with a drone
    public ExploreGround(Drone d) {
        this.d = d;
        this.start = d.getDirection();
    }

    @Override
    public String getDecision() {
        // Stop if battery is low
        if (!d.sufficientBattery()) {
            return d.stop();
        } else if (state == State.FINDGROUND) {
            return findGroundPhase(); // Fly to ground
        } else if (state == State.FLY) {
            return d.fly();
        } else if (state == State.R_ECHO) {
            return echoPhase();
        } else if (state == State.UTURN1 || state == State.UTURN2 || state == State.UTURN4) {
            return uTurnPhase();
        }
        return d.stop();
    }

    // Method to handle the find ground phase
    public String findGroundPhase() {
        if (groundDirection != d.getDirection()) {
            return d.setDirection(groundDirection);
        }
        groundDistance--;
        return d.fly();
    }

    // Method to handle the U-turn phase
    public String uTurnPhase() {
        if (state == State.UTURN1) {
            start = d.getDirection();
            return d.fly();
        } else if (state == State.UTURN2) {
            turn++;
            if (groundDirection == start.right()) {
                return d.right();
            } else {
                return d.left();
            }
        } else if (state == State.UTURN4) {
            groundDistance -= d.getSafeRange();
            if (groundDirection == start.right()) {
                return d.left();
            } else {
                return d.right();
            }
        }
        return d.stop();
    }

    // Method to handle the echo phase
    public String echoPhase() {
        if (this.state == State.R_ECHO) {
            groundDirection = d.getDirection().right();
            return d.echo("R");
        }
        return d.stop();
    }

    @Override
    public boolean getResponse(JSONObject response) {
        // Deduct cost
        d.deductCost(translate.getCost(response));

        if (state == State.FINDGROUND) {
            if (groundDistance == 0) return true;
        } else if (state == State.FLY) {
            state = State.R_ECHO;
        } else if (state == State.R_ECHO) {
            return getEchoResponse(response);
        } else if (state == State.UTURN1 || state == State.UTURN2 || state == State.UTURN4) {
            return uTurnGetResponse();
        }
        return false;
    }

    // Method to handle the echo response
    public boolean getEchoResponse(JSONObject response) {
        if (state == State.R_ECHO) {
            echoRightResponse = response;
            if (d.isGround(response)) {
                state = State.UTURN1;
                groundDistance = translate.getRange(response);
            } else {
                state = State.FLY;
            }
        }
        return false;
    }

    // Method to handle the U-turn response
    public boolean uTurnGetResponse() {
        if (state == State.UTURN1) {
            state = State.UTURN2;
        } else if (state == State.UTURN2) {
            if (turn != 2) {
                state = State.UTURN2;
            } else {
                state = State.UTURN4;
            }
        } else if (state == State.UTURN4) {
            state = State.FINDGROUND;
        }
        return false;
    }
}
