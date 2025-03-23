package ca.mcmaster.se2aa4.island.team44;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


public class Drone{
    private Location location;
    private Battery battery;
    private Compass direction;
    private Phases phase;
    private JSONTranslator translator= new Translator(); 
    private POI POI;
    private final Logger logger = LogManager.getLogger();
    private Compass startDir;


    public Drone(int battery, Compass direction){
        this.location = new Location(0,0);
        this.battery = new Battery(battery);
        this.direction = direction;
        phase = Phases.GROUND;
        POI = new POI();
        this.startDir =Compass.E;
        logger.info("startDIRRRRRRRRR"+startDir);
    }

    public Compass getStartDir(){
        return this.startDir;
    }

    public void setStartDir(Compass dir){
        this.startDir=dir;
    }

    public void switchPhase(){
        phase.switchPhase();
    }

    public Location getLocation(){
        return this.location;
    }

    public void setLocation(int x, int y){
        this.location = new Location(x,y);
    }

    public String fly(){
        this.location = this.location.makeMove(this.direction);
        this.setLocation(this.location.getXCoord(), this.location.getYCoord());
        return translator.fly(); 
    }

    public void deductCost(int cost){
        battery.useBudget(cost);
    }

    public int checkBattery(){
        return battery.getCurrentBudget();
    }

    public Compass getDirection(){
        return this.direction;
    }
    public String right() {
        this.fly(); 
        this.direction = this.direction.right();
        this.fly();
        return translator.heading(this.getDirection());
    }

    public String left(){
        logger.info("Compare this btw "+this.direction);
        this.fly();
        this.direction = this.direction.left();
        this.fly();
        return translator.heading(this.getDirection());
    }



    public String echo(String dir){
        if (dir.equals("F")){
            return translator.echo(this.direction);
        }else if (dir.equals("R")){
            return translator.echo(this.direction.right());
        }else{
            return translator.echo(this. direction.left());
        }
        
    }

    public String setDirection(Compass direction){
        this.direction = direction;
        return translator.heading(this.direction);
    }

    public void addEmergencySite(EmergencySite emergencySite){
        POI.addEmergencySite(emergencySite);
    }

    public EmergencySite getESite(){
        return POI.getEmergencySites();
    }

    public void addCreek(Creeks creek){
        POI.addCreek(creek);
    }

    public List<Creeks> getCreek(){
       return POI.getCreeks();
    }

    @Override
    public String toString(){
        return "Location: "+location.toString() + " Direction: "+this.direction;
    }

    public Creeks getClosestCreek(){
        return POI.getClosestCreek();
    }

    public boolean ifPossiblyFound(){
        logger.info ("here in found");
        return POI.getEmergencySites()!=null&&POI.getCreeks().size()>0;
    }
    public boolean sufficientBattery(){
        return this.battery.sufficientBattery();
    }

    public boolean isGround(JSONObject response){
        return translator.ground(response);

    }

}