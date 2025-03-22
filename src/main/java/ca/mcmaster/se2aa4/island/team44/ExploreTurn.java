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
   Compass start;
   int groundDistance = -1;
   Steps step=Steps.ECHOF;
   int LCount =0;
   int RCount=0;


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
               turnLeft(start, d);
               return translator.heading(d.getDirection());
           }
           case Steps.F -> {
               d.fly();
               return translator.fly();
           }
           case Steps.L3 ->{
               turnLeft(start, d);
               LCount ++;
               logger.info("l" + LCount);
               return translator.heading( d.getDirection());
           }
        
           case Steps.R1 ->{
               turnRight(start, d);
               RCount++;
               logger.info("R" + RCount);
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
   }


   private void turnLeft(Compass start,Drone d){
       if(start==Compass.N || start == Compass.E){
           d.left();               
       }else{
           d.right();
       }
   }


   private void turnRight(Compass start,Drone d){
       if(start==Compass.N || start == Compass.E){
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
               }else if (0<LCount && LCount <2){
                   step = Steps.L3;
               }
               break;
           }
     
           case Steps.R1-> {
               if (RCount == 2){
                   step = Steps.ECHOT;
               }else if(0 < RCount && RCount< 2 ){
                   step=Steps.R1;
               }
               break;
           }
   
           case Steps.FG->{
               if(groundDistance==1 || groundDistance==0){
                   return true;// go back to explore forward 
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
