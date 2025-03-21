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
        ECHOR,
        ECHOT,
        FR,
        END,
        CONTINUE;
    }

public class ExploreTurn implements ExplorerPhase{
    Drone d;
    private Explorer explorer;
    private final Logger logger = LogManager.getLogger();
    JSONTranslator translator = new Translator();
    Compass start; 
    boolean turned= false;
    int groundDistance = -1;
    Steps step=Steps.ECHOF;
    public ExploreTurn(Drone d){
        this.d=d;
        this.start=d.getDirection();
        logger.info("Starting phase at"+start);
    }


    public String getDecision(){
        //Stop if Battery Low
        if(!d.sufficientBattery()){
        logger.info("**Low Battery: Returning to Base");
        return translator.stop();
        }
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
                logger.info("booo "+d.getClosestCreek());
                return translator.stop();
            }case Steps.ECHOR ->{
                if(d.getDirection()==Compass.N)
                    return translator.echo(d.getDirection().right());
                else
                    return translator.echo(d.getDirection().left());
            }
            case Steps.FR->{
                d.fly();
                return translator.fly();
            }
            default ->{
                d.fly();
                return translator.fly();
            }
        }
        //return translator.stop();

    
    }
 
    public boolean getResponse(JSONObject response){
        d.deductCost(translator.getCost(response));
        logger.info("**Battery" + d.checkBattery());
        switch(step){
            case Steps.ECHOF -> {
                if(translator.getFound(response).equals("GROUND")){
                    step=Steps.FG;         
                    groundDistance=translator.getRange(response);

                }else if(translator.getFound(response).equals("OUT_OF_RANGE")){
                    if(translator.getRange(response)<=2)
                        step=Steps.L; //skip to uturn uturn
                    else 
                        step=Steps.FR; //fly until no longer have ground to the right then uturn
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

                if(d.ifPossiblyFound()){
                    Location closest = d.getClosestCreek().getLocation(); 
                    Location site= d.getESite().getLocation(); 
                    logger.info("hardtime");
                    logger.info("my creeks: "+d.getCreek());
                    logger.info("closest creek "+d.getClosestCreek());
                    logger.info("my site "+d.getESite());
                    logger.info("where");
                    logger.info("location to site: "+d.getLocation().calculateDistance(site.getLocation())+" site to closest: "+(site.getLocation().calculateDistance(closest.getLocation())));
                    if(Math.abs(d.getLocation().getXCoord()-site.getLocation().getXCoord())>(Math.abs(closest.getLocation().getXCoord()-site.getLocation().getXCoord()))){
                        logger.info("MAYBE bebebbe");
                        step = Steps.END; 
                    }

//                    if(d.getLocation().calculateDistance(site.getLocation())>(site.getLocation().calculateDistance(closest.getLocation()))){

                    break;
                }
            }
            case Steps.CONTINUE->{
                return true;   
            }
            case Steps.FR->{
                step=Steps.ECHOR;
                break;
            }
            case Steps.ECHOR->{
                logger.info("DO U EXIST");
                if(translator.getRange(response)>=1)
                    step=Steps.L; //safe to do uturn
                else
                    step=Steps.FR;
                break;
            }
    

        }
 
    return false;
    }
   
}