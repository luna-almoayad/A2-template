package ca.mcmaster.se2aa4.island.team44;
import org.json.JSONObject;

public class DroneCommandTranslator implements DroneCommandAdapter {
   
    @Override
    public String stop() {
        return new JSONObject().put("action", "stop").toString();
    }

    @Override
    public String fly() {
        return new JSONObject().put("action", "fly").toString();
    }

    @Override
    public String echo(Compass direction) {
        return new JSONObject()
            .put("action", "echo")
            .put("parameters", new JSONObject().put("direction", direction.toString()))
            .toString();
    }

    @Override
    public String heading(Compass direction) {
        return new JSONObject()
            .put("action", "heading")
            .put("parameters", new JSONObject().put("direction", direction.toString()))
            .toString();
    }

    @Override
    public String scan() {
        return new JSONObject().put("action", "scan").toString();
    }
}
