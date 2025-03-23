package ca.mcmaster.se2aa4.island.team44;
import org.json.JSONObject;


public interface JSONTranslator{

    //Methods to acquire basic information
    String getHeading(JSONObject info);
    int getCost(JSONObject info);
    String getStatus(JSONObject info);
    JSONObject getExtraInfo(JSONObject info);

    //Methods for Echo Response
    int getRange(JSONObject info);
    String getFound(JSONObject info);

    //Methods for Scan Response
    String getBiomes(JSONObject info);
    String getSiteIDs(JSONObject info);
    String getCreekIDs(JSONObject info);
    boolean hasCreek(JSONObject info);
    boolean hasESite(JSONObject info);
    boolean hasOcean(JSONObject info);

    //Methods for Actions
    String stop();
    String fly();
    String echo(Compass direction);
    String heading(Compass direction);
    String scan();

}