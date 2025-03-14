package ca.mcmaster.se2aa4.island.team44;

public enum Phases {
    GROUND,
    GRIDSEARCH,
    UTURN;


    public Phases switchPhase() {
        switch (this) {
            case GROUND: return GRIDSEARCH;
            case GRIDSEARCH: return UTURN;
            case UTURN: return GRIDSEARCH;
            default: return this;
        }
    }
}
