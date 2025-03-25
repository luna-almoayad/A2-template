package ca.mcmaster.se2aa4.island.team44.control;

import ca.mcmaster.se2aa4.island.team44.drones.Drone;

public class MissionReport{
    Drone d; 
    // constructor to initialize drone 
    public MissionReport(Drone d){
        this.d = d; 
    }
    
    // method to generate report based on cases 
    public String generateReport(){
        StringBuilder mission = new StringBuilder();
        // if creek is found and sufficient battery output creek and esite 
        if(d.sufficientBattery()&&d.ifPossiblyFound()){
            mission.append("Creek ID:").append(d.getClosestCreek().getID()).append("\n");
            mission.append("Emergency Site ID:").append(d.getESite().getID()).append("\n");
        // if not sufficent battery, quit mission and report to user 
        }else if(!d.sufficientBattery()){ 
            mission.append("Insufficent Battery: Return to Base");
        // else output that mission unable to be completed (ex. invalid spawn position)
        }else mission.append("Creek and Emergency Site were not found: Return to Base");

        return mission.toString();
    }
    
}