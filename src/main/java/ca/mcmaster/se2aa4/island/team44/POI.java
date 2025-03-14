package ca.mcmaster.se2aa4.island.team44;
import java.util.*;

public class POI { //shouldnt be abstract
    private ArrayList<Creeks> creeks;
    private EmergencySite esite;

    

    public POI(){
        creeks=new ArrayList<Creeks>();
        this.esite = null;
    }
    

    public void addCreek(Creeks creek){
        creeks.add(creek);
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



}
