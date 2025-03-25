package ca.mcmaster.se2aa4.island.team44.site;
import java.util.*;

public class POI { //shouldnt be abstract
    // List to store creeks
    private List<Creek> creeks;
    
    // Emergency site associated with the POI
    private EmergencySite esite;
    
    // Closest creek to the emergency site
    Creek closest = null; 

    // Constructor to initialize the creeks list and emergency site
    public POI(){
        creeks = new ArrayList<Creek>();
        this.esite = null;  
    }
    
    // Method to add a creek to the list and update the closest creek
    public void addCreek(Creek creek){
        creeks.add(creek);
        closest = creek;
    }

    // Method to set the emergency site
    public void addEmergencySite(EmergencySite emergencySite){
        this.esite = emergencySite;
    }

    // Method to get the list of creeks
    public List<Creek> getCreeks(){
        return this.creeks;
    }

    // Method to get the emergency site
    public EmergencySite getEmergencySites(){
        return this.esite;
    }

    // Private method to find the closest creek to the emergency site
    private Creek findClosestCreek(){
        Creek closest = creeks.get(0); 
        for (Creek creek : creeks){
            if (esite.getLocation().calculateDistance(creek.getLocation()) < esite.getLocation().calculateDistance(closest.getLocation())){
                closest = creek;
            }
        }
        return closest; 
    }

    // Method to get the closest creek
    public Creek getClosestCreek(){
        return findClosestCreek();
    }
}
