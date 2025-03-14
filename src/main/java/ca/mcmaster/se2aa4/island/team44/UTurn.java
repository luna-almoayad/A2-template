package ca.mcmaster.se2aa4.island.team44;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

enum Turns{
    L1,
    R1,
    R2,
    F1,
    R3,
    FLY,
    ECHOR,
    ECHOL,
    STOP;
}

public class UTurn implements ExplorerPhase{
   
    Turns turns = Turns.L1;
    Drone d;
    Translator translator = new Translator();
    private final Logger logger = LogManager.getLogger();
    Compass start;


    public UTurn(Drone d){
        this.d=d;
        this.start=d.getDirection();
    }



    public boolean getResponse(JSONObject response){

        switch(turns){
            case Turns.L1 -> {
                turns = Turns.R1;
                break;
            }
            //d.setDirection(d.getDirection().left());
            case Turns.R1 -> { 
                turns = Turns.R2;
                //d.setDirection(d.getDirection().right());
                break;
            }
            case Turns.R2 -> {
                turns = Turns.F1;
                //d.setDirection(d.getDirection().right());
                break;
            }
            case Turns.F1 ->{
                turns = Turns.R3;
                break;
            }

            case Turns.R3 ->{
                turns = Turns.FLY;
                break;
            }
            //d.setDirection(d.getDirection().right());
            case Turns.FLY -> {
                turns = Turns.ECHOR;
                break;
            }
            case Turns.ECHOR ->{
                turns=Turns.ECHOL;
                if(translator.getFound(response).equals("OUT_OF_RANGE") && !d.getCreek().isEmpty())
                    turns = Turns.STOP;
                break;
            }
            case Turns.ECHOL ->{
                //turns=Turns.L1;
                if(translator.getFound(response).equals("OUT_OF_RANGE") && !d.getCreek().isEmpty()){
                    turns = Turns.STOP;
                }else{
                    turns=Turns.L1;
                }
                break;
            }
            case Turns.STOP ->{
                turns =  Turns.STOP;
                break;
            }
             default -> {
            
            }
         }
        if( turns == Turns.L1){
            return true;
        }
        return false;
    }

    public String getDecision(){

        switch(turns){
            case Turns.L1 -> {
               if(start==Compass.N)
                    d.left();
                else
                    d.right();
                turns = Turns.R1;
                logger.info("Comparing "+d.getDirection());
                return translator.heading(d.getDirection());
            }
    
            case Turns.R1 -> { 
                turns = Turns.R2;
                if(start==Compass.N)
                    d.right();
                else
                    d.left();
                return translator.heading(d.getDirection());
            }

            case Turns.R2 -> {
                turns = Turns.F1;
                if(start==Compass.N)
                    d.right();
                else
                    d.left();
                //d.setDirection(d.getDirection().right());
                return translator.heading(d.getDirection());
            }

            case Turns.F1 -> {
                turns = Turns.R3;
                
                d.fly();
                return translator.fly();
            }

            case Turns.R3 -> {
                if(start==Compass.N)
                    d.right();
                else
                    d.left();
                turns = Turns.FLY;
                //d.setDirection(d.getDirection().right());
                return translator.heading(d.getDirection());
            }

            case Turns.FLY -> {
                d.fly(); 
                return translator.fly();
            }
            case Turns.ECHOR -> {
                return translator.echo(d.getDirection().right());
            } 
            case Turns.ECHOL -> {
                return translator.echo(d.getDirection().left());
            } 
            case Turns.STOP ->{
                return translator.stop();
            }   
            default -> {
                return "YOU SHOUDNL HAVE GOT HERE LOL";
            }
    }



        
    }
}