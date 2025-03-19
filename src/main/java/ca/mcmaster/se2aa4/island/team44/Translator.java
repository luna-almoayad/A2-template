package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

//getHeading(JSONObject): String
//getCost(JSONObject): String
//getExtraInfo(JSONObject):String


public class Translator{

    private final Logger logger = LogManager.getLogger();

    public String getHeading(JSONObject info){
        if(info.has("heading")) return info.getString("heading");
            throw new IllegalArgumentException("heading not found");
    }

    public String getCost(JSONObject info){
        if(info.has("cost")) return info.getString("cost");
                throw new IllegalArgumentException("cost not found");

    }

    public String getStatus(JSONObject info){
        if(info.has("status")) return info.getString("status");
            throw new IllegalArgumentException("status not found");
    }

    public JSONObject getExtraInfo(JSONObject info){
        if(info.has("extras")) return info.getJSONObject("extras");
            throw new IllegalArgumentException("extras not found");
    }

//echo response
    public int getRange(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);

        if(extraInfo.has("range")) return extraInfo.getInt("range");
            throw new IllegalArgumentException("range not found");
    }

    public String getFound(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);

        if(extraInfo.has("found")) return extraInfo.getString("found");
            throw new IllegalArgumentException("Found not found");
    }

    
//scan response

    public String getBiomes(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);
        JSONArray biomes;
        if(extraInfo.has("biomes"))  biomes = extraInfo.optJSONArray("biomes");
        else
            return null;

        String[] result = new String[biomes.length()];
        for (int i = 0; i < biomes.length(); i++) {
            result[i] = biomes.optString(i, ""); 
        }
        return result[0] ;
    }
    //returns biomes 

    public String getSiteIDs(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);

        JSONArray creeks;

        if(extraInfo.has("sites"))  creeks = extraInfo.optJSONArray("sites");
        else
            return null;

        String[] result = new String[creeks.length()];
        for (int i = 0; i < creeks.length(); i++) {
            result[i] = creeks.optString(i, ""); 
        }
        if(result.length == 0)
            return null;
        return result[0] ;
    }

    public String getCreekIDs(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);

        JSONArray creeks;

        if(extraInfo.has("creeks"))  creeks = extraInfo.optJSONArray("creeks");
        else
            return null;

        String[] result = new String[creeks.length()];
        for (int i = 0; i < creeks.length(); i++) {
            result[i] = creeks.optString(i, ""); 
        }
        if(result.length == 0)
            return null;
        return result[0] ;
    }

    public boolean hasCreek(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);
        JSONArray creeks=extraInfo.optJSONArray("creeks");
        return (creeks!=null && creeks.length()>0);
    }

    public boolean hasESite(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);
        JSONArray sites=extraInfo.optJSONArray("sites");
        return (sites!=null && sites.length()>0);
    }

    public boolean hasOcean(JSONObject info){
        logger.info("bro ur in");
        JSONObject extraInfo = getExtraInfo(info);
        JSONArray biomes=extraInfo.optJSONArray("biomes");
        if (biomes == null) return false; 
        boolean hasOceans=false;
        logger.info("biomes length "+biomes.length()+" "+biomes.optString(0, ""));
        for (int i = 0; i < biomes.length(); i++) {
            if (biomes.optString(i, "").equals("OCEAN"))
                hasOceans = true;
        }
        
        return hasOceans&&biomes.length()==1; //&&biomes.length()==1
    }


//action
    public String stop(){
        JSONObject decision = new JSONObject();
        decision.put("action", "stop");

        return decision.toString();

    }

    public String fly(){
        JSONObject decision = new JSONObject();
        decision.put("action", "fly");
        return decision.toString();

    }

//NEEDS TO BE CHANGED TO ENUM
    public String echo(Compass direction){
       JSONObject decision = new JSONObject();
       decision.put("action", "echo");
       decision.put("parameters", new JSONObject().put("direction", direction.toString()) );
       return decision.toString();
    }


//NEEDS TO BE CHANGED TO ENUM
    public String heading(Compass direction){
        JSONObject decision = new JSONObject();
        decision.put("action", "heading");
        decision.put( "parameters", new JSONObject().put("direction", direction.toString() ));
        return decision.toString();
    }

    public String scan(){
       JSONObject decision = new JSONObject();
       decision.put("action", "scan");
       return decision.toString();
    }

}