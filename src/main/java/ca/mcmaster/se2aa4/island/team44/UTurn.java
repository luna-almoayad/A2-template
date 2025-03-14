package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONObject;

enum Turns{
    L1,
    R1,
    L2,
    F1,
    R3,
    FLY;
}

public class UTurn implements ExplorerPhase{
   
    public UTurn(){

    }

    Turns turns = Turns.L1;

    Translator translator = new Translator();



    public boolean getResponse(JSONObject response){
        if( turns == Turns.FLY) return true;
        return false;
    }

    public String getDecision(Drone d){

        switch(turns){
            case Turns.L1:
                turns = Turns.R1;
                d.setDirection(d.getDirection().left());
                return translator.heading(d.getDirection().left());
    
            case Turns.R1: 
                turns = Turns.L2;
                d.setDirection(d.getDirection().right());
                return translator.heading(d.getDirection().right());

            case Turns.L2:
                turns = Turns.F1;
                d.setDirection(d.getDirection().left());
                return translator.heading(d.getDirection().left());

            case Turns.F1:
                turns = Turns.R3;
                d.fly();
                return translator.fly();

            case Turns.R3:
                turns = Turns.F1;
                d.setDirection(d.getDirection().right());
                return translator.heading(d.getDirection().right());

            case Turns.FLY: 
                d.fly(); 
                return translator.fly();
            default:     
                return "YOU SHOUDNL HAVE GOT HERE LOL";
    }



        
    }
}