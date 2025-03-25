package ca.mcmaster.se2aa4.island.team44.explorer;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team44.drones.Drone;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataAdapter;
import ca.mcmaster.se2aa4.island.team44.json.JSONDataParser;
import ca.mcmaster.se2aa4.island.team44.navigation.Compass;

// Enum to represent the different steps in the exploration turn
enum Steps {
    ECHOF,  // Echo forward
    L,      // Turn left
    F,      // Fly forward
    L3,     // Turn left three times
    R1,     // Turn right once
    FG,     // Fly to ground
    ECHOR,  // Echo right
    ECHOT,  // Echo turn
    FR,     // Fly right
    END;    // End of the exploration turn
}

public class ExploreTurn implements ExplorerPhase {
    // Drone instance
    Drone d;
    
    // JSON data adapter for parsing JSON data
    JSONDataAdapter translator = new JSONDataParser();
    
    // Starting direction of the turn
    Compass startTurn;
    
    // Distance to the ground
    int groundDistance = -1;
    
    // Current step in the exploration turn
    Steps step = Steps.ECHOF;
    
    // Counters for left and right turns
    int LCount = 0;
    int RCount = 0;

    // Constructor to initialize the ExploreTurn with a drone
    public ExploreTurn(Drone d) {
        this.d = d;
        this.startTurn = d.getDirection();
    }

    // Method to get the decision for the next action
    public String getDecision() {
        if (!d.sufficientBattery()) { 
            return d.stop();  
        } else if (step == Steps.ECHOF) {
            return d.echo("F");
        } else if (step == Steps.L) {
            return turnLeft(startTurn, d);
        } else if (step == Steps.F) {
            return d.fly();
        } else if (step == Steps.L3) {
            LCount++;
            return turnLeft(startTurn, d);
        } else if (step == Steps.R1) {
            RCount++;
            return turnRight(startTurn, d);
        } else if (step == Steps.ECHOR) {
            if (d.getDirection() == Compass.N) {
                return d.echo("R");
            } else {
                return d.echo("L");
            }
        } else if (step == Steps.FR) {
            return d.fly();
        } else {
            return d.fly();
        } 
    }   

    // Private method to turn the drone left
    private String turnLeft(Compass startTurn, Drone d) {
        if (startTurn == Compass.N) {
            return d.left();               
        } else {
            return d.right();
        }
    }

    // Private method to turn the drone right
    private String turnRight(Compass startTurn, Drone d) {
        if (startTurn == Compass.N) {
            return d.right();               
        } else {
            return d.left();
        }
    }

    // Method to process the response from the drone's action
    public boolean getResponse(JSONObject response) {
        d.deductCost(translator.getCost(response));

        if (step == Steps.ECHOF) {
            if (d.isGround(response)) {
                return true;        
            } else if (!d.isGround(response)) {
                if (translator.getRange(response) <= 2) {
                    step = Steps.L; // Skip to U-turn 
                } else {
                    step = Steps.FR; // Fly until no longer have ground to the right then U-turn
                }
            }
        } else if (step == Steps.L) {
            step = Steps.F;
        } else if (step == Steps.F) {
            step = Steps.L3;
        } else if (step == Steps.L3) {
            if (LCount == 3) {
                step = Steps.R1;
            } else if (0 < LCount && LCount < 2) {
                step = Steps.L3;
            }
        } else if (step == Steps.R1) {
            if (RCount == 2) {
                return true;
            } else if (0 < RCount && RCount < 2) {
                step = Steps.R1;
            }
        } else if (step == Steps.FR) {
            step = Steps.ECHOR;
        } else if (step == Steps.ECHOR) {
            if (translator.getRange(response) > 1) {
                step = Steps.L; // Safe to do U-turn
            } else {
                step = Steps.FR;
            }
        }
        return false;
    }
}
