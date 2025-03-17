package ca.mcmaster.se2aa4.island.team44;

public class MissionReport{

    public String generateReport(POI poi){
        StringBuilder mission = new StringBuilder();
        mission.append("Creek ID:").append(poi.getClosestCreek().getID()).append("\n");
        mission.append("Emergency Site ID:").append(poi.getEmergencySites().getID()).append("\n");
        return mission.toString();

    }


    
}