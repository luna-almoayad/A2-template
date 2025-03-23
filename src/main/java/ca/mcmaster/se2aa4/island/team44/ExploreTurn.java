package ca.mcmaster.se2aa4.island.team44;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


enum Steps{
       ECHOF,
       L,
       F,
       L3,
       R1,
       FG,
       ECHOR,
       ECHOT,
       FR,
       END;
   }


public class ExploreTurn implements ExplorerPhase{
   Drone d;
   private final Logger logger = LogManager.getLogger();
   JSONTranslator translator = new Translator();
   Compass startTurn;
   int groundDistance = -1;
   Steps step=Steps.ECHOF;
   int LCount =0;
   int RCount=0;
   Compass startDir;


   public ExploreTurn(Drone d){
       this.d=d;
       this.startTurn=d.getDirection();
       logger.info("startTurning phase at"+startTurn);
       this.startDir=d.getStartDir();
       logger.info("startDIR phase at"+startDir);
   }

   public String getDecision(){
       //Stop if Battery Low
       if(!d.sufficientBattery()){
        logger.info("**Low Battery: Returning to Base");
        return translator.stop();
       }
        logger.info("**Step" + step);

       if (step== Steps.ECHOF){
        return translator.echo(d.getDirection());
       }
       else if (step== Steps.L){
        turnLeft(startTurn, d);
        return translator.heading(d.getDirection());
       }
       else if (step==Steps.F){
        d.fly();
        return translator.fly();
       }
       else if (step == Steps.L3){
        turnLeft(startTurn, d);
        LCount ++;
        logger.info("l" + LCount);
        return translator.heading( d.getDirection());
       }
       else if (step == Steps.R1){
        turnRight(startTurn, d);
        RCount++;
        logger.info("R" + RCount);
        return translator.heading( d.getDirection());
       }
       else if (step == Steps.ECHOR){
        if((d.getDirection()==Compass.N&&startDir==Compass.W)||(d.getDirection()==Compass.S&&startDir==Compass.E)){
            return translator.echo(d.getDirection().right());
        }else{
            return translator.echo(d.getDirection().left());
        }
       }
       else if (step == Steps.FR){
        d.fly();
        return translator.fly();
       }else{
        d.fly();
        return translator.fly();
       }
      
}
   


   private void turnLeft(Compass startTurn,Drone d){
       if((startTurn==Compass.N &&startDir==Compass.E) || (startTurn==Compass.S &&startDir==Compass.W)){
           d.left();               
       }else{
           d.right();
       }
   }


   private void turnRight(Compass startTurn,Drone d){
       if((startTurn==Compass.N &&startDir==Compass.E) || (startTurn==Compass.S &&startDir==Compass.W)){
           d.right();               
       }else{
           d.left();
       }
   }

   public boolean getResponse(JSONObject response){
       d.deductCost(translator.getCost(response));
       logger.info("**Battery" + d.checkBattery());

       if (step == Steps.ECHOF){
        if(translator.getFound(response).equals("GROUND")){
            return true;        
        }else if(translator.getFound(response).equals("OUT_OF_RANGE")){
            if(translator.getRange(response)<=2){
                step=Steps.L; //skip to uturn uturn
            }else{
                step=Steps.FR; //fly until no longer have ground to the right then uturn
            }
        }
        } else if (step == Steps.L){
            step=Steps.F;
        } else if (step == Steps.F){
            step=Steps.L3;
        } else if (step == Steps.L3){
            if (LCount==3){
                step= Steps.R1;
            }else if (0< LCount && LCount <2){
                step = Steps.L3;
            }
        }else if (step == Steps.R1){
            if (RCount == 2){
                return true;
            }else if(0 < RCount && RCount< 2 ){
                step=Steps.R1;
            }
        }else if (step == Steps.FR){
            step=Steps.ECHOR;
        }else if (step == Steps.ECHOR){
            logger.info("DO U EXIST");
            if(translator.getRange(response)>1){
                step=Steps.L; //safe to do uturn
            }else{
                step=Steps.FR;
            }
        }
      
    return false;
    }
 
}
