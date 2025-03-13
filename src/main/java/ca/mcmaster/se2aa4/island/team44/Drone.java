package ca.mcmaster.se2aa4.island.team44;

public class Drone{
    private Location location;
    private Battery battery;
    private Compass direction;
    private Phases phase;

    public Drone(int battery){
        this.location = new Location(0,0);
        this.battery = new Battery(battery);
        direction = Compass.E;
        phase = Phases.GROUND;
    }

    public void switchPhase(){
        phase.switchPhase();
    }


    public Location getLocation(){
        return location;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public void fly(){
        location.makeMove(direction);
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

    public void setDirection(Compass direction){
        this.direction = direction;
    }



}