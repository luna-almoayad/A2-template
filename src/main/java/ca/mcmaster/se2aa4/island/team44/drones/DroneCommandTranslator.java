package ca.mcmaster.se2aa4.island.team44.drones;


import ca.mcmaster.se2aa4.island.team44.navigation.*;
import org.json.JSONObject;

public class DroneCommandTranslator implements DroneCommandAdapter {
   
    // write stop command to JSON 
    @Override
    public String stop() {
        return new JSONObject().put("action", "stop").toString();
    }

    // write fly command to JSON
    @Override
    public String fly() {
        return new JSONObject().put("action", "fly").toString();
    }

    // write echo command to JSON
    @Override
    public String echo(Compass direction) {
        return new JSONObject()
            .put("action", "echo")
            .put("parameters", new JSONObject().put("direction", direction.toString()))
            .toString();
    }

    // write echo command to JSON
    @Override
    public String heading(Compass direction) {
        return new JSONObject()
            .put("action", "heading")
            .put("parameters", new JSONObject().put("direction", direction.toString()))
            .toString();
    }

    // write scan command to JSON
    @Override
    public String scan() {
        return new JSONObject().put("action", "scan").toString();
    }
}
