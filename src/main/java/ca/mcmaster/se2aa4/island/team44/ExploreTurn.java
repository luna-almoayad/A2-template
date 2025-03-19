package ca.mcmaster.se2aa4.island.team44;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

enum Steps{
        ECHOF,
        F1,
        F2,
        F3,
        L,
        F,
        L2,
        L3,
        L4,
        R1,
        R2,
        FG,
        ECHOT,
        END,
        CONTINUE;
    }

public class ExploreTurn implements ExplorerPhase{
    Drone d;
    private Explorer explorer;
    Translator translator = new Translator();
    private final Logger logger = LogManager.getLogger();
    Compass start; 
    boolean turned= false;
    int groundDistance = -1;
    Steps step=Steps.ECHOF;
 
    public ExploreTurn(Drone d){
        this.d=d;
        this.start=d.getDirection();
        logger.info("Starting phase at"+start);
    }

 
    @Override
    public String getDecision(){
        switch(step){
            case Steps.ECHOF ->{
                return translator.echo(d.getDirection());
            }
            case Steps.L ->{
                if(start==Compass.N || start == Compass.E)
                    d.left();                
                else
                    d.right();
                return translator.heading(d.getDirection());
            }
            case Steps.F -> {
                d.fly();
                return translator.fly();
            }
            case Steps.L2 ->{
               if(start==Compass.N || start == Compass.E)
                    d.left();                
                else
                    d.right();
                return translator.heading( d.getDirection());
            }
            case Steps.L3 ->{
                if(start==Compass.N || start == Compass.E)
                    d.left();                
                else
                    d.right();
                return translator.heading( d.getDirection());
            }case Steps.L4 ->{
                if(start==Compass.N || start == Compass.E)
                    d.left();                
                else
                    d.right();
                return translator.heading( d.getDirection());
            }case Steps.R1 ->{
                if(start==Compass.N || start == Compass.E)
                    d.right();                
                else
                    d.left();
                return translator.heading( d.getDirection());
            }case Steps.R2 ->{
                if(start==Compass.N || start == Compass.E)
                    d.right();                
                else
                    d.left();
                return translator.heading( d.getDirection());
            }case Steps.ECHOT ->{
                return translator.echo(d.getDirection() );
            }case Steps.END->{
                return translator.stop();
            }default ->{
                d.fly();
                return translator.fly();
            }
        }
        //return translator.stop();

    
    }
 
    public boolean getResponse(JSONObject response){
        switch(step){
            case Steps.ECHOF -> {
                if(translator.getFound(response).equals("GROUND")){
                    step=Steps.FG;         
                    groundDistance=translator.getRange(response);

                }else if(translator.getFound(response).equals("OUT_OF_RANGE")){
                    if(translator.getRange(response)<=2)
                        step=Steps.L; //skip to uturn uturn
                    else 
                        step=Steps.F1; //fly twice then uturn
                }
                break;
            }

            case Steps.F1-> {
                step= Steps.F2;
                break;
            }
            case Steps.F2 ->{
                step=Steps.F3;
                break;
                }
            case Steps.F3 ->{
                step=Steps.L;
                break;
            }
            case Steps.L -> {
                step=Steps.F;
                break;
                }
            case Steps.F->{
                step=Steps.L2;
                break;
            } 
            case Steps.L2->{

             step=Steps.L3;
             break; 
             }
            case Steps.L3-> {step=Steps.L4;break;}
            case Steps.L4-> {step=Steps.R1; break;}
            case Steps.R1-> { step=Steps.R2; break;} 
            case Steps.R2-> {step=Steps.ECHOT; break;}
            case Steps.FG->{
                if(groundDistance==1 || groundDistance==0){
                    return true;
                }
                groundDistance--;
            break;
            }case Steps.ECHOT->{
                if( translator.getFound(response).equals("OUT_OF_RANGE") ) step = Steps.END;
                else{ 
                    groundDistance=translator.getRange(response);
                    step = Steps.FG;
                }
                break;
            }
            case Steps.CONTINUE->{
                return true;   
            }
    

        }
 
    return false;
    }
 
   
}