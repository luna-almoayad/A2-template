package ca.mcmaster.se2aa4.island.team44.explorer;

public enum Phases {
    SPIN,        // Initial phase where the explorer spins
    SPAWN,       // Phase where the explorer spawns
    GROUND,      // Phase where the explorer is on the ground
    GRIDSEARCH,  // Phase where the explorer performs a grid search
    UTURN,       // Phase where the explorer makes a U-turn
    CHECKCONTINUE; // Phase where the explorer checks if it should continue

    // Method to switch to the next phase
    public Phases switchPhase() {
        if (this == SPIN) {
            return SPAWN;
        } else if (this == SPAWN) {
            return GROUND;
        } else if (this == GROUND) {
            return GRIDSEARCH;
        } else if (this == GRIDSEARCH) {
            return UTURN;
        } else if (this == UTURN) {
            return CHECKCONTINUE;
        } else if (this == CHECKCONTINUE) {
            return GRIDSEARCH;
        } else {
            return this;
        }
    }
}
