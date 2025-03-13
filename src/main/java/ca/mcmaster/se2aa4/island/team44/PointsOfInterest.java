package ca.mcmaster.se2aa4.island.team44;
import java.util.*;

public abstract class PointsOfInterest {
    protected Location location; 

    public PointsOfInterest(Location location){
        this.location= location; 
    }
    
    public Location getLocation(){
        return location; 
    }
}
