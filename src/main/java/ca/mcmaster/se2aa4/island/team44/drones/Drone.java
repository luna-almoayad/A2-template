package ca.mcmaster.se2aa4.island.team44.drones;

import ca.mcmaster.se2aa4.island.team44.json.*;
import ca.mcmaster.se2aa4.island.team44.navigation.*;


import java.util.List;

import org.json.JSONObject;


public class Drone extends DroneInfo{
    private JSONDataAdapter translator= new JSONDataParser(); 
    private DroneCommandAdapter command= new DroneCommandTranslator();


    public Drone(int battery, Compass direction){
        super(battery, direction);
    }

    public boolean ifSafeRange(int range){
        return range>3;
    }

    public int getSafeRange(){
        return 3;
    }

    public String fly(){
        this.location = this.location.makeMove(this.direction);
        this.setLocation(this.location.getXCoord(), this.location.getYCoord());
        return command.fly(); 
    }

    
    public String right() {
        this.fly(); 
        this.direction = this.direction.right();
        this.fly();
        return command.heading(this.getDirection());
    }

    public String left(){
        this.fly();
        this.direction = this.direction.left();
        this.fly();
        return command.heading(this.getDirection());
    }

    public String scan(){
        return command.scan();
    }

    public String setDirection(Compass direction){
        super.direction = direction;
        return command.heading(this.direction);
    }

    public String echo(String dir){
        if (dir.equals("F")){
            return command.echo(this.direction);
        }else if (dir.equals("R")){
            return command.echo(this.direction.right());
        }else{
            return command.echo(this. direction.left());
        }
        
    }

    @Override
    public String toString(){
        return "Location: "+location.toString() + " Direction: "+this.direction;
    }


    public boolean isGround(JSONObject response){
        return translator.ground(response);
    }
    public String stop(){
        return command.stop();
    }

}