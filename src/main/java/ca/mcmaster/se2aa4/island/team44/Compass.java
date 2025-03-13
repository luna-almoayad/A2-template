package ca.mcmaster.se2aa4.island.team44;

public enum Compass{
    NORTH,
    EAST, 
    SOUTH, 
    WEST;

    public Compass Right(){
        switch (this){
            case NORTH: return EAST;
            case EAST: return SOUTH;
            case SOUTH: return WEST;
            case WEST: return NORTH; 
            default: return this;
        }
    }

    public Compass left(){
         switch (this){
            case NORTH: return WEST;
            case EAST: return NORTH;
            case SOUTH: return EAST;
            case WEST: return SOUTH; 
            default: return this;
        }
    
    }
    

}