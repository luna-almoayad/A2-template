package ca.mcmaster.se2aa4.island.team44;


public class MissionReport{
    Drone d; 
    public MissionReport(Drone d){
        this.d = d; 
    }
    public String generateReport(){
        StringBuilder mission = new StringBuilder();
        if(d.sufficientBattery()&&d.ifPossiblyFound()){
            mission.append("Creek ID:").append(d.getClosestCreek().getID()).append("\n");
            mission.append("Emergency Site ID:").append(d.getESite().getID()).append("\n");

        }else if(!d.sufficientBattery()){ 
            mission.append("Insufficent Battery: Return to Base");
        }else mission.append("Creek and Emergency Site were not found: Return to Base");

        return mission.toString();
    }
    
}