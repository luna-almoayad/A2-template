package ca.mcmaster.se2aa4.island.team44.explorer;

import ca.mcmaster.se2aa4.island.team44.drones.Drone;
//below is factory design pattern
public class ExplorerFactory {

    // Method to get the appropriate ExplorerPhase based on the given phase and drone
    public ExplorerPhase getPhase(Phases phase, Drone d) {
        if (phase == Phases.SPIN) {
            return new ExploreSpin(d);
        } else if (phase == Phases.GRIDSEARCH) {
            return new ExploreForward(d);
        } else if (phase == Phases.GROUND) {
            return new ExploreGround(d);
        } else if (phase == Phases.SPAWN) {
            return new ExploreSpawn(d);
        } else if (phase == Phases.UTURN) {
            return new ExploreTurn(d);
        } else if (phase == Phases.CHECKCONTINUE) {
            return new ExploreEnd(d);
        }
        return null;
    }
}