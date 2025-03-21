package ca.mcmaster.se2aa4.island.team44;
import java.util.*;

public class POI { //shouldnt be abstract
    private ArrayList<Creeks> creeks;
    private EmergencySite esite;
    private Location location; 
    Creeks closest= null; 

    public POI(){
        creeks=new ArrayList<Creeks>();
        this.esite = null;  
    }
    
    public void addCreek(Creeks creek){
        creeks.add(creek);
        closest=creek;
    }

    public void addEmergencySite(EmergencySite emergencySite){
        this.esite= emergencySite;
    }

    public ArrayList<Creeks> getCreeks(){
        return this.creeks;
    }

    public EmergencySite getEmergencySites(){
        return this.esite;
    }

    public Creeks findClosestCreek(){
        Creeks closest= creeks.get(0); 
        for (Creeks creek : creeks){
            if (esite.getLocation().calculateDistance(creek.getLocation()) <= esite.getLocation().calculateDistance(closest.getLocation())){
                closest = creek;
            }
        }
        return closest; 
    }

    public Creeks getClosestCreek(){
        return findClosestCreek();
    }


}
