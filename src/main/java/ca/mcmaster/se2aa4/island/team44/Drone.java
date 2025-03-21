package ca.mcmaster.se2aa4.island.team44;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Drone{
    private Location location;
    private Battery battery;
    private Compass direction;
    private Phases phase;
    private POI POI;
    private final Logger logger = LogManager.getLogger();
    private Compass startDir;


    public Drone(int battery, Compass direction){
        this.location = new Location(0,0);
        this.battery = new Battery(battery);
        this.direction = direction;
        phase = Phases.GROUND;
        POI = new POI();
        this.startDir = Compass.E;
    }

    public Compass getStartDir(){
        return this.startDir;
    }

    public void setStartDir(Compass dir){
        this.startDir=dir;
    }

    public void switchPhase(){
        phase.switchPhase();
    }


    public Location getLocation(){
        return this.location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public void fly(){
        this.location = this.location.makeMove(this.direction);
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
    public void right() {
        this.fly();
        this.direction = this.direction.right();
    }

    public void left(){
        logger.info("Compare this btw "+this.direction);
        this.fly();
        this.direction = this.direction.left();
    }

    public void setDirection(Compass direction){
        this.direction = direction;
    }

    public void addEmergencySite(EmergencySite emergencySite){
        POI.addEmergencySite(emergencySite);
    }

    public EmergencySite getESite(){
        return POI.getEmergencySites();
    }

    public void addCreek(Creeks creek){
        POI.addCreek(creek);
    }

    public ArrayList<Creeks> getCreek(){
       return POI.getCreeks();
    }

    public String toString(){
        return "Location: "+location.toString() + " Direction: "+this.direction;
    }

    public Creeks getClosestCreek(){
        return POI.getClosestCreek();
    }


}