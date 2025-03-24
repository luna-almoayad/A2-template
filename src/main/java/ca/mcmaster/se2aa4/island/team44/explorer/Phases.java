package ca.mcmaster.se2aa4.island.team44.explorer;

public enum Phases {
    SPIN,
    SPAWN,
    GROUND,
    GRIDSEARCH,
    UTURN,
    CHECKCONTINUE;


    public Phases switchPhase() {
        if(this==SPIN){
            return SPAWN;
        }
        else if(this == SPAWN){
            return GROUND;
        }
        else if (this == GROUND){
            return GRIDSEARCH; 
        }
        else if(this == GRIDSEARCH){
            return UTURN;
        }
        else if(this == UTURN){
            return CHECKCONTINUE; 
        }else if(this == CHECKCONTINUE){
            return GRIDSEARCH;
        }
        else{
            return this; 
        }
          
    }
}
