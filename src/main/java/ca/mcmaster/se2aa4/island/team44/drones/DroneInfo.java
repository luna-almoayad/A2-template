package ca.mcmaster.se2aa4.island.team44.drones;
import java.util.List;

import ca.mcmaster.se2aa4.island.team44.navigation.Compass;
import ca.mcmaster.se2aa4.island.team44.navigation.Location;
import ca.mcmaster.se2aa4.island.team44.site.Creeks;
import ca.mcmaster.se2aa4.island.team44.site.EmergencySite;
import ca.mcmaster.se2aa4.island.team44.site.POI;
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

    public Location getLocation(){
        return this.location;
    }

    public void setLocation(int x, int y){
        this.location = new Location(x,y);
    }

    public void deductCost(int cost){
        battery.useBudget(cost);
    }

    public int checkBattery(){
        return battery.getCurrentBudget();
    }

    public Compass getDirection(){
        return this.direction;
    }

    public void addEmergencySite(EmergencySite emergencySite){
        POI.addEmergencySite(emergencySite);
    }

    public EmergencySite getESite(){
        return POI.getEmergencySites();
    }

    public void addCreek(Creek creek){
        POI.addCreek(creek);
    }

    public List<Creek> getCreeks(){
       return POI.getCreeks();
    }


    public Creek getClosestCreek(){
        return POI.getClosestCreek();
    }

    public boolean ifPossiblyFound(){
        return POI.getEmergencySites()!=null&&POI.getCreeks().size()>0;
    }
    public boolean sufficientBattery(){
        return this.battery.sufficientBattery();
    }
}