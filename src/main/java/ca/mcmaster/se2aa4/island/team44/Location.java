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
            case NORTH: return new Location(x, y-1);
            case SOUTH: return new Location(x, y-1);
            case WEST: return new Location (x-1, y);
            case EAST: return new Location (x+1,y);
            default: return this;

        }

    }*/
}
