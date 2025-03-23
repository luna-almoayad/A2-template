package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

//Implement adapter for target interface JSONTranslator that clients will use 
public class JSONDataParser implements JSONDataAdapter{

    private final Logger logger = LogManager.getLogger();

    @Override
    public String getHeading(JSONObject info){
        if(info.has("heading")){ 
            return info.getString("heading");
        }else{
            throw new IllegalArgumentException("heading not found");
        }
    }

    @Override
    public int getCost(JSONObject info){
        if(info.has("cost")){ 
            return (info.getInt("cost"));
        }else {
            throw new IllegalArgumentException("cost not found");
        }
    }

    @Override
    public String getStatus(JSONObject info){
        if(info.has("status")){ 
            return info.getString("status");
        }else{  
            throw new IllegalArgumentException("status not found");
        }
    }

    @Override
    public JSONObject getExtraInfo(JSONObject info){
        if(info.has("extras")){ 
            return info.getJSONObject("extras");
        }else{
            throw new IllegalArgumentException("extras not found");
        }
    }

    @Override
    public int getRange(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);
        if(extraInfo.has("range")){ 
            return extraInfo.getInt("range");
        }else{
            throw new IllegalArgumentException("range not found");
        }
    }

    @Override
    public String getFound(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);
        if(extraInfo.has("found")){
            return extraInfo.getString("found");
        }else{
            throw new IllegalArgumentException("Found not found");
        }
    }

    
//scan response
    @Override
    public String getBiomes(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);
        JSONArray biomes;
        if(extraInfo.has("biomes")){
            biomes = extraInfo.optJSONArray("biomes");
        }else{
            return null;
        }
        String[] result = new String[biomes.length()];
        for (int i = 0; i < biomes.length(); i++) {
            result[i] = biomes.optString(i, ""); 
        }
        return result[0] ;
    }
    //returns biomes 
    
    @Override
    public String getSiteIDs(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);
        JSONArray sites;

        if(extraInfo.has("sites")){  
            sites = extraInfo.optJSONArray("sites");
        }else{
            return null;
        }
        String[] result = new String[sites.length()];
        for (int i = 0; i < sites.length(); i++) {
            result[i] = sites.optString(i, ""); 
        }
        if(result.length == 0){
            return null;
        }
        return result[0] ;
    }

    @Override
    public String getCreekIDs(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);
        JSONArray creeks;

        if(extraInfo.has("creeks")){  
            creeks = extraInfo.optJSONArray("creeks");
        }else{
            return null;
        }   
        String[] result = new String[creeks.length()];
        for (int i = 0; i < creeks.length(); i++) {
            result[i] = creeks.optString(i, ""); 
        }
        if(result.length == 0){
            return null;
        }
        return result[0];
    }

    @Override
    public boolean hasCreek(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);
        JSONArray creeks=extraInfo.optJSONArray("creeks");
        return (creeks!=null && creeks.length()>0);
    }

    @Override
    public boolean hasESite(JSONObject info){
        JSONObject extraInfo = getExtraInfo(info);
        JSONArray sites=extraInfo.optJSONArray("sites");
        return (sites!=null && sites.length()>0);
    }

    @Override
    public boolean hasOcean(JSONObject info){
        logger.info("bro ur in");
        JSONObject extraInfo = getExtraInfo(info);
        JSONArray biomes=extraInfo.optJSONArray("biomes");
        if (biomes == null){ 
            return false; 
        }
        boolean hasOceans=false;
        logger.info("biomes length "+biomes.length()+" "+biomes.optString(0, ""));
        for (int i = 0; i < biomes.length(); i++) {
            if (biomes.optString(i, "").equals("OCEAN")){
                hasOceans = true;
            }
        }
        
        return hasOceans&&biomes.length()==1; //&&biomes.length()==1
    }


   

    public boolean ground(JSONObject response){
        if (getFound(response).equals("OUT_OF_RANGE")){
            return false; 
        }else{
            return true; 
        }
    }

}