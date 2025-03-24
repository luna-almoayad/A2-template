package ca.mcmaster.se2aa4.island.team44.explorer;

import org.json.JSONObject;

//this may be state pattern interface
public interface ExplorerPhase{

    String getDecision();
    boolean getResponse(JSONObject response); 

}