package ca.mcmaster.se2aa4.island.team44;
import java.util.*;

public class POI { //shouldnt be abstract
    private List<Creek> creeks;
    private EmergencySite esite;
    Creek closest= null; 

    public POI(){
        creeks=new ArrayList<Creek>();
        this.esite = null;  
    }
    
    public void addCreek(Creek creek){
        creeks.add(creek);
        closest=creek;
    }

    public void addEmergencySite(EmergencySite emergencySite){
        this.esite= emergencySite;
    }

    public List<Creek> getCreeks(){
        return this.creeks;
    }

    public EmergencySite getEmergencySites(){
        return this.esite;
    }

    private Creek findClosestCreek(){
        Creek closest= creeks.get(0); 
        for (Creek creek : creeks){
            if (esite.getLocation().calculateDistance(creek.getLocation()) < esite.getLocation().calculateDistance(closest.getLocation())){
                closest = creek;
            }
        }
        return closest; 
    }

    public Creek getClosestCreek(){
        return findClosestCreek();
    }


}
