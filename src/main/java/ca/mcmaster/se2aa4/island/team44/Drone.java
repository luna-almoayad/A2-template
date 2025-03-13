package ca.mcmaster.se2aa4.island.team44;

public class Drone{
    private Location location;
    private Battery battery;
    private Compass direction;
    private Phases phase;

    public Drone(Location location, int battery, Phase phase){
        this.location = location;
        Battery battery = new Battery(battery);
        direction = EAST;
        phase = GROUND;
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
        battery.useBudget(battery);
    }

    public int checkBattery(){
        return battery.getCurrentBudget();
    }

    public int getDirection(){
        return this.direction;
    }

    public void setDirection(Compass direction){
        this.direction = direction;
    }




}