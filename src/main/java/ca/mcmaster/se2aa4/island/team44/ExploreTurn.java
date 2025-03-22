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
   private Explorer explorer;
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


       switch(step){
           case Steps.ECHOF ->{
               return translator.echo(d.getDirection());
           }
           case Steps.L ->{
               turnLeft(startTurn, d);
               return translator.heading(d.getDirection());
           }
           case Steps.F -> {
               d.fly();
               return translator.fly();
           }
           case Steps.L3 ->{
               turnLeft(startTurn, d);
               LCount ++;
               logger.info("l" + LCount);
               return translator.heading( d.getDirection());
           }
           case Steps.R1 ->{
               turnRight(startTurn, d);
               RCount++;
               logger.info("R" + RCount);
               return translator.heading( d.getDirection());
           }case Steps.ECHOR ->{
               if((d.getDirection()==Compass.N&&startDir==Compass.W)||(d.getDirection()==Compass.S&&startDir==Compass.E))
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
       switch(step){
           case Steps.ECHOF -> {
               if(translator.getFound(response).equals("GROUND")){
                   return true;        
               }else if(translator.getFound(response).equals("OUT_OF_RANGE")){
                   if(translator.getRange(response)<=2)
                       step=Steps.L; //skip to uturn uturn
                   else
                       step=Steps.FR; //fly until no longer have ground to the right then uturn
               }
               break;
           }


           case Steps.L -> {
               step=Steps.F;
               break;
               }
           case Steps.F->{
               step=Steps.L3;
               break;
           }

           case Steps.L3-> {
               if (LCount==3){
                   step= Steps.R1;
               }else if (0< LCount && LCount <2){
                   step = Steps.L3;
               }
               break;
           }
     
           case Steps.R1-> {
               if (RCount == 2){
                   return true;
               }else if(0 < RCount && RCount< 2 ){
                   step=Steps.R1;
               }
               break;
           }
           case Steps.FR->{
               step=Steps.ECHOR;
               break;
           }
           case Steps.ECHOR->{
               logger.info("DO U EXIST");
               if(translator.getRange(response)>1)
                   step=Steps.L; //safe to do uturn
               else
                   step=Steps.FR;
               break;
           }

       }
   return false;
   }
 
}
