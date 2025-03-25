package ca.mcmaster.se2aa4.island.team44.site;
import ca.mcmaster.se2aa4.island.team44.navigation.Location;

public class Creek {

    // ID of the creek
    private String id; 
    
    // Location of the creek
    private Location location;
    
    // Constructor to initialize the creek with an ID and location
    public Creek(String id, Location location) {
        this.id = id;
        this.location = location;
    }
    
    // Method to get the location of the creek
    public Location getLocation() {
        return location; 
    }

    // Method to get the ID of the creek
    public String getID() {
        return this.id; 
    }

    // Method to return the string representation of the creek's location
    @Override
    public String toString() {
        return location.toString();
    }
}

