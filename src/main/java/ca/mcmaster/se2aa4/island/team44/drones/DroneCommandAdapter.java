package ca.mcmaster.se2aa4.island.team44.drones;

import ca.mcmaster.se2aa4.island.team44.navigation.Compass;

// interface for drone command that implements drone pattern 
public interface DroneCommandAdapter {
    //Methods for Actions
    String stop();
    String fly();
    String echo(Compass direction);
    String heading(Compass direction);
    String scan();
}
