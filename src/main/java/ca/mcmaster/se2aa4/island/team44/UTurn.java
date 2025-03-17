package ca.mcmaster.se2aa4.island.team44;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

enum Turns{
    CHECKF,
    L1,
    R1,
    R2,
    F1,
    R3,
    FLY,
    FLY1,
    FLY2,
    ECHOF,
    STOP,
    ONCOAST,
    FLY3;
}

public class UTurn implements ExplorerPhase{
    private Explorer explorer;
    Turns turns = Turns.L1;
    Drone d;
    Translator translator = new Translator();
    private final Logger logger = LogManager.getLogger();
    Compass start;
    boolean turned= false;
    int groundDistance = -1;


    public UTurn(Drone d){
        this.d=d;
        this.start=d.getDirection();
        logger.info("Starting phase at"+start);
    }



    public boolean getResponse(JSONObject response){
        turned = false;
        logger.info("sheesh "+d.getDirection());

        switch(turns){
            
            case Turns.CHECKF -> {
                turns= Turns.L1;
                if (translator.getFound(response).equals("GROUND")){
                    groundDistance = translator.getRange(response);
                    turns=Turns.FLY;
                }
                break;
            }
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
                turns = Turns.ECHOF;
                break;
            }
            case Turns.FLY->{
                groundDistance--;
                if(groundDistance==0)
                    return true;
                break;

            }
            //d.setDirection(d.getDirection().right());
            case Turns.FLY1 -> {
                turns = Turns.FLY2;
                //turned= true;
                break;
            }
            case Turns.FLY2 ->{
                turns= Turns.L1;
                break;
            }

            case Turns.ECHOF -> {
                turns= Turns.FLY1;
                String found = translator.getFound(response);
                if(found.equals("GROUND")){
                    groundDistance = translator.getRange(response)+2;
                    turns = Turns.ONCOAST;
            }
                
                if(translator.getFound(response).equals("OUT_OF_RANGE") ){
                    logger.info("stopsposish");
                    logger.info(explorer.deliverFinalReport());
                    turns = Turns.STOP;
                }

                break;
            } case Turns.ONCOAST ->{
                if(groundDistance == 0) return true;
            }
           /* case Turns.ECHOR ->{
                turns=Turns.ECHOL;
                if(translator.getFound(response).equals("OUT_OF_RANGE") && d.getDirection().right()==d.getStartDir() )
                    turns = Turns.STOP;
                else if (d.getESite()!=null)
                    turns = Turns.STOP;

                break;
            }
            case Turns.ECHOL ->{
                //turns=Turns.L1;
                if(translator.getFound(response).equals("OUT_OF_RANGE") && d.getDirection().right()==d.getStartDir()){
                    turns = Turns.STOP;
                }else if (d.getESite()!=null){
                    turns = Turns.STOP;
                
                }else{
                    turns= Turns.FLY;
                    
                }
                break;
            }
            */
            case Turns.STOP ->{
                break;
            }
             default -> {
            
            }
        }
        return turned;
    }

    public String getDecision(){

        switch(turns){

            case Turns.CHECKF ->{
                return translator.echo(d.getDirection());
            }
            case Turns.L1 -> {
               if(start==Compass.N || start == Compass.E)
                    d.left();
                else
                    d.right();
            
                logger.info("Comparing "+d.getDirection());
                return translator.heading(d.getDirection());
            }
    
            case Turns.R1 -> { 
                if(start==Compass.N || start == Compass.E){
                    d.right();
                }else{
                    d.left();
                }
                return translator.heading(d.getDirection());
            }

            case Turns.R2 -> {
                if(start==Compass.N || start == Compass.E){
                    d.right();
                }else{
                    d.left();
                }
                //d.setDirection(d.getDirection().right());
                return translator.heading(d.getDirection());
            }

            case Turns.F1 -> {
                
                d.fly();
                return translator.fly();
            }

            case Turns.R3 -> {
                if(start==Compass.N || start == Compass.E){
                    d.right();
                } else{
                    d.left();
                }
                //d.setDirection(d.getDirection().right());
                return translator.heading(d.getDirection());
            }

            case Turns.FLY1 -> {
                d.fly(); 
                return translator.fly();
            }
            case Turns.FLY2 ->{
                d.fly();
                return translator.fly();
            }

            case Turns.ECHOF ->{
                return translator.echo(d.getDirection());
            }

            case Turns.ONCOAST -> {
               // if (groundDistance == 0 ) return translator.stop();
                groundDistance = groundDistance - 1;
                d.fly();
                return translator.fly();
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