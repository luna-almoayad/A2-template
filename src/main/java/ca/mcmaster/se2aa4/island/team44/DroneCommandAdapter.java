package ca.mcmaster.se2aa4.island.team44;

public interface DroneCommandAdapter {
    //Methods for Actions
    String stop();
    String fly();
    String echo(Compass direction);
    String heading(Compass direction);
    String scan();
}
