package ca.mcmaster.se2aa4.island.team44;

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
/*
    public Location makeMove(Compass compass){
        switch (compass){
            case N: return new Location(x, y-1);
            case S: return new Location(x, y-1);
            case W: return new Location (x-1, y);
            case E: return new Location (x+1,y);
            default: return this;

        }

    }

    public int calculateDistance(Location target){
        return Math.abs(this.x - target.x) + Math.abs(this.y-target.y);
    }
    
    public int getYDist(Location target){
        return (target.y - this.y);
    }

    public int getXDist(Location target){
        return (target.x - this.y);
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

    }*/
}
