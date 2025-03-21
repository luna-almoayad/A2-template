package ca.mcmaster.se2aa4.island.team44;

public class ExplorerFactory{

    public ExplorerPhase getPhase(Phases phase, Drone d){

        if( phase == Phases.GRIDSEARCH){
            return new ExploreForward(d);

        }else if( phase == Phases.GROUND){
             return new ExploreGround(d); 

        }else if( phase == Phases.SPAWN){
            return new ExploreSpawn(d);

        }else if( phase == Phases.UTURN){
            return new ExploreTurn(d);
        }else if(phase == Phases.CHECKCONTINUE){
            return new ExploreEnd(d);
        }

        return null;

    }
}