package ca.mcmaster.se2aa4.island.team44;

public class Controller{
    Drone drone;
    PointOfInterests POI;
    Translator translator;
    Phase phase;

    public Controller(){
        drone = new Drone();
        POI = new PointOfInterests();
        translator = new Translator();
        phase = new ExploreGround();
    }

    public String getDecision(){
        return phase.takeDecision(Drone drone);
    }

    public String get_response(String s, Drone d){
        phase.get_response(s, d);
    }


    }
