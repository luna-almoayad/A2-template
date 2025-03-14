package ca.mcmaster.se2aa4.island.team44;

public class Drone{
    private Location location;
    private Battery battery;
    private Compass direction;
    private Phases phase;
    private POI POI;

    public Drone(int battery){
        this.location = new Location(0,0);
        this.battery = new Battery(battery);
        direction = Compass.E;
        phase = Phases.GROUND;
        POI = new POI();
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
        location.makeMove(this.direction);
    }

    public void changeCost(int cost){
        battery.useBudget(cost);
    }

    public void setBattery(int cost){
        battery.useBudget(cost);
    }

    public int checkBattery(){
        return battery.getCurrentBudget();
    }

    public Compass getDirection(){
        return this.direction;
    }
    public void right() {
        fly();
        direction = direction.right();
    }

    public void left(){
        fly();
        direction = direction.left();
    }

    public void setDirection(Compass direction){
        this.direction = direction;
    }

    public void addEmergencySite(EmergencySite emergencySite){
        POI.addEmergencySite(emergencySite);
    }

    public void addCreek(Creeks creek){
        POI.addCreek(creek);
    }


}