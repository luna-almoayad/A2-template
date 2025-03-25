package ca.mcmaster.se2aa4.island.team44.navigation;

public class Location {
    // Immutable x-coordinate
    private final int x;
    
    // Immutable y-coordinate
    private final int y;

    // Constructor to initialize the location with x and y coordinates
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Method to get the x-coordinate
    public int getXCoord() {
        return this.x;
    }

    // Method to get the y-coordinate
    public int getYCoord() {
        return this.y;
    }

    // Method to create a new location based on the current location and a compass direction
    public Location makeMove(Compass compass) {
        if (compass == Compass.N) {
            return new Location(x, y + 1);
        } else if (compass == Compass.S) {
            return new Location(x, y - 1);
        } else if (compass == Compass.W) {
            return new Location(x - 1, y);
        } else if (compass == Compass.E) {
            return new Location(x + 1, y);
        }
        return this;
    }

    // Method to calculate the distance between the current location and a target location
    public double calculateDistance(Location target) {
        return Math.pow((Math.pow((this.x - target.x), 2) + Math.pow((this.y - target.y), 2)), 0.5);
    }

    // Method to get the y-distance between the current location and a target location
    public int getYDist(Location target) {
        return target.y - this.y;
    }

    // Method to get the x-distance between the current location and a target location
    public int getXDist(Location target) {
        return target.x - this.x;
    }

    // Method to return the string representation of the location
    @Override 
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}


