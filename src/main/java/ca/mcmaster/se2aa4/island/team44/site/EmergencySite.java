package ca.mcmaster.se2aa4.island.team44.site;

import ca.mcmaster.se2aa4.island.team44.navigation.Location;

public class EmergencySite {

    // Location of the emergency site
    Location location;
    
    // ID of the emergency site
    String id;

    // Constructor to initialize the emergency site with an ID and location
    public EmergencySite(String id, Location location) {
        this.location = location;
        this.id = id;
    }

    // Method to set the location of the emergency site
    public void setLocation(Location location) {
        this.location = location;
    }

    // Method to get the location of the emergency site
    public Location getLocation() {
        return this.location;
    }

    // Method to get the ID of the emergency site
    public String getID() {
        return this.id;
    }

    // Method to return the string representation of the emergency site's location
    @Override
    public String toString() {
        return this.location.toString();
    }
}


