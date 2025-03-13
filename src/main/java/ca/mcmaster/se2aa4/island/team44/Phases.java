package ca.mcmaster.se2aa4.island.team44;

public enum Phases {
    GROUND,
    COAST,
    SPIRAL;

    public Phases switchPhase() {
        switch (this) {
            case GROUND: return COAST;
            case COAST: return SPIRAL;
            case SPIRAL: return SPIRAL; 
            default: return this;
        }
    }
}
