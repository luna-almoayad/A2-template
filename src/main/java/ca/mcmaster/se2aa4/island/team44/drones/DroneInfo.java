package ca.mcmaster.se2aa4.island.team44.drones;
import java.util.List;

import ca.mcmaster.se2aa4.island.team44.navigation.Compass;
import ca.mcmaster.se2aa4.island.team44.navigation.Location;
import ca.mcmaster.se2aa4.island.team44.site.Creek;
import ca.mcmaster.se2aa4.island.team44.site.EmergencySite;
import ca.mcmaster.se2aa4.island.team44.site.POI;

// Drone Info class that hol
public class DroneInfo{
    protected Location location;
    protected Battery battery;
    protected Compass direction;
    protected POI POI;

    public DroneInfo(int battery, Compass direction){
        this.location = new Location(0,0);
        this.battery = new Battery(battery);
        this.direction = direction;
        POI = new POI();
    }

    // method to get drone location
    public Location getLocation(){
        return this.location;
    }

    // method to set drone location
    public void setLocation(int x, int y){
        this.location = new Location(x,y);
    }

    // method to deduct cost with actions 
    public void deductCost(int cost){
        battery.useBudget(cost);
    }

    // methods to check current battery 
    public int checkBattery(){
        return battery.getCurrentBudget();
    }

    // method to get drone direction
    public Compass getDirection(){
        return this.direction;
    }

    //method to add emergency site 
    public void addEmergencySite(EmergencySite emergencySite){
        POI.addEmergencySite(emergencySite);
    }

    //method to get emergency site 
    public EmergencySite getESite(){
        return POI.getEmergencySites();
    }

    // method to add creek 
    public void addCreek(Creek creek){
        POI.addCreek(creek);
    }

    //method to get list of creeks 
    public List<Creek> getCreeks(){
       return POI.getCreeks();
    }

    //method to get closest creek 
    public Creek getClosestCreek(){
        return POI.getClosestCreek();
    }

    // method to determine whether the esite and creeks are possibly found 
    public boolean ifPossiblyFound(){
        return POI.getEmergencySites()!=null&&POI.getCreeks().size()>0;
    }

    // method to determine whether we have enough battery to return to base 
    public boolean sufficientBattery(){
        return this.battery.sufficientBattery();
    }
}