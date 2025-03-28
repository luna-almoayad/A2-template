package ca.mcmaster.se2aa4.island.team44.control;

import ca.mcmaster.se2aa4.island.team44.json.*;
import ca.mcmaster.se2aa4.island.team44.navigation.*;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import eu.ace_design.island.bot.IExplorerRaid;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    protected JSONDataAdapter translate = new JSONDataParser();
    Controller control;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject( new JSONTokener( new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);

        //initialize controller to begin mission 
        control = new Controller(batteryLevel, Compass.toEnum(direction) );
        logger.info(Compass.toEnum(direction));
    }


    @Override
    public String takeDecision() {
        
        String decision = control.getDecision();
        logger.info("** Decisions: {}", decision);
        //once mission is stopped, return report
        if (decision.toLowerCase().contains("stop")){
            deliverFinalReport();
        }
        return decision;
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);

        control.getResponse(response);


    }

    @Override
    public String deliverFinalReport() {
        // deliver final report from mission 
        String finalReport= control.finishMission();
        logger.info("Final Info\n" + finalReport);
        return finalReport;
    }

}
