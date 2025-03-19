package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


public interface JSONTranslator{

    //Methods to acquire basic information
    public String getHeading(JSONObject info);
    public int getCost(JSONObject info);
    public String getStatus(JSONObject info);
    public JSONObject getExtraInfo(JSONObject info);

    //Methods for Echo Response
    public int getRange(JSONObject info);
    public String getFound(JSONObject info);

    //Methods for Scan Response
    public String getBiomes(JSONObject info);
    public String getSiteIDs(JSONObject info);
    public String getCreekIDs(JSONObject info);
    public boolean hasCreek(JSONObject info);
    public boolean hasESite(JSONObject info);
    public boolean hasOcean(JSONObject info);

    //Methods for Actions
    public String stop();
    public String fly();
    public String echo(Compass direction);
    public String heading(Compass direction);
    public String scan();

}