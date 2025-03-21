package ca.mcmaster.se2aa4.island.team44;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExploreEnd implements ExplorerPhase{
    
    Drone d;
    private Explorer explorer;
    private JSONTranslator translator;
    private final Logger logger = LogManager.getLogger();
    POI poi; 

    public ExploreEnd(Drone d){
        this.d=d;
    }

    


}
