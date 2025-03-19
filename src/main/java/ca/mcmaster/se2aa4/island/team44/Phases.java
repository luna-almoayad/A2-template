package ca.mcmaster.se2aa4.island.team44;

public enum Phases {
    GROUND,
    GRIDSEARCH,
    UTURN;


    public Phases switchPhase() {
        if (this == GROUND){
            return GRIDSEARCH; 
        }
        else if(this == GRIDSEARCH){
            return UTURN;

        }
        else if(this == UTURN){
            return GRIDSEARCH; 
        }
        else{
            return this; 
        }
          
    }
}
