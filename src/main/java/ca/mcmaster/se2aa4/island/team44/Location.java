package ca.mcmaster.se2aa4.island.team44;
import java.util.Objects;

public class Location {
    private final int x; //immutable objects
    private final int y;

    public Location(int x, int y ){
        this.x= x;
        this.y=y;
    }

    public int getXCoord(){
        return this.x;
    }

    public int getYCoord(){
        return this.y;
    }

    public Location getLocation(){
        return this;
    }

    public Location makeMove(Compass compass){
        if (compass == Compass.N){
            return new Location(x, y+1);
        }
        else if (compass == Compass.S){
            return new Location(x, y-1);
        }
        else if (compass == Compass.W){
            return new Location (x-1, y);
        }
        else if (compass == Compass.E){
            return new Location (x+1,y);
        }
        return this;
    }

    public double calculateDistance(Location target){
        return Math.pow((Math.pow((this.x - target.x),2) + Math.pow((this.y-target.y),2)),0.5);
    }
   
    
    public int getYDist(Location target){
        return target.y - this.y;
    }

    public int getXDist(Location target){
        return target.x - this.x;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        } 
        if (o == null || this.getClass() != o.getClass()){
            return false;
        }
        Location other = (Location) o;
        return this.x == other.x && this.y == other.y;

    }

    @Override 
    public int hashCode(){
        return Objects.hash(this.x, this.y);
    }

    @Override 
    public String toString(){
        return "(" + x + "," + y + ")";

    }
}

 
