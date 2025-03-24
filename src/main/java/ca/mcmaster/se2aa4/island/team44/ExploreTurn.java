package ca.mcmaster.se2aa4.island.team44;
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
   JSONDataAdapter translator = new JSONDataParser();
   Compass startTurn;
   int groundDistance = -1;
   Steps step=Steps.ECHOF;
   int LCount =0;
   int RCount=0;

   public ExploreTurn(Drone d){
       this.d=d;
       this.startTurn=d.getDirection();
   }

   public String getDecision(){
       if(!d.sufficientBattery()){ 
            return d.stop();  
       }if (step== Steps.ECHOF){
            return d.echo("F");
       }else if (step== Steps.L){
            return turnLeft(startTurn, d);
       }else if (step==Steps.F){
            return d.fly();
       }else if (step == Steps.L3){
            LCount ++;
            return turnLeft(startTurn, d);
       }else if (step == Steps.R1){
            RCount++;
            return turnRight(startTurn, d);
       }else if (step == Steps.ECHOR){
            if((d.getDirection()==Compass.N)){
                return d.echo("R");
            }else{
                return d.echo("L");
            }
       }else if (step == Steps.FR){
            return d.fly();
       }else{
            return d.fly();
       } 
    }   

   

   private String turnLeft(Compass startTurn,Drone d){
       if((startTurn==Compass.N )){
           return d.left();               
       }else{
           return d.right();
       }
   }


   private String turnRight(Compass startTurn,Drone d){
       if((startTurn==Compass.N)){
           return d.right();               
       }else{
           return d.left();
       }
   }

   public boolean getResponse(JSONObject response){
       d.deductCost(translator.getCost(response));

       if (step == Steps.ECHOF){
            if(d.isGround(response)){
                return true;        
            }else if(!d.isGround(response)){
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
            if(translator.getRange(response)>1){
                step=Steps.L; //safe to do uturn
            }else{
                step=Steps.FR;
            }
        }
    return false;
    }
}
