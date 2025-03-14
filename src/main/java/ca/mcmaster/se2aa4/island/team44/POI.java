package ca.mcmaster.se2aa4.island.team44;

public abstract class POI {
    protected Location location; 

    public POI(Location location){
        this.location= location; 
    }
    
    public Location getLocation(){
        return this.location; 
    }
}
