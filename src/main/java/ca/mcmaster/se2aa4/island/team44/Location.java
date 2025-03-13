package ca.mcmaster.se2aa4.island.team44;
import java.util.*;

public class Location {
    private int x;
    private int y;

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

    public Location makeMove(Compass compass){
        switch (compass){
            case NORTH: return new Location(x, y-1);
            case SOUTH: return new Location(x, y-1);
            case WEST: return new Location (x-1, y);
            case EAST: return new Location (x+1,y);
            default: return this;

        }

    }

    public int calculateDistance(Location target){
        return Math.abs(this.x - target.x) + Math.abs(this.y-target.y);
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        } 
        if (o == null || this.getClass() != o.getClass()){
            return false;
        }
        Location o = (Location) o;
        return this.x == o.x && this.y == o.y;

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

