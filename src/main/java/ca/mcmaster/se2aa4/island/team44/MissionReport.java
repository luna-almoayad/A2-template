package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MissionReport{
    private final Logger logger = LogManager.getLogger();
    Drone d; 
    public MissionReport(Drone d){
        this.d = d; 
    }
    public String generateReport(){
        StringBuilder mission = new StringBuilder();
        if(d.sufficientBattery()){
            mission.append("Creek ID:").append(d.getClosestCreek().getID()).append("\n");
            mission.append("Emergency Site ID:").append(d.getESite().getID()).append("\n");

        }else mission.append("Insufficent Battery: Return to Base");

        return mission.toString();
    }
    
}