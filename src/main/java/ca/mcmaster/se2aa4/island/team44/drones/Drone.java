package ca.mcmaster.se2aa4.island.team44.drones;

import ca.mcmaster.se2aa4.island.team44.json.*;
import ca.mcmaster.se2aa4.island.team44.navigation.*;


import java.util.List;

import org.json.JSONObject;


public class Drone extends DroneInfo{
    // initialize necessary translator as their interface types rather than concrete 
    private JSONDataAdapter translator= new JSONDataParser(); 
    private DroneCommandAdapter command= new DroneCommandTranslator();


    public Drone(int battery, Compass direction){
        super(battery, direction);
    }

    //method to determine if safe to turn 
    public boolean ifSafeRange(int range){
        return range>3;
    }

    // get safe amount to turn 
    public int getSafeRange(){
        return 3;
    }

    // method to fly the drone 
    public String fly(){
        //change location and append to json 
        this.location = this.location.makeMove(this.direction);
        this.setLocation(this.location.getXCoord(), this.location.getYCoord());
        return command.fly(); 
    }

    
    //method to turn right 
    public String right() {
        // Adjust drone location and append to json 
        this.fly(); 
        this.direction = this.direction.right();
        this.fly();
        return command.heading(this.getDirection());
    }

    //method to turn left 
    public String left(){
        // adjust drone position and append to json 
        this.fly();
        this.direction = this.direction.left();
        this.fly();
        return command.heading(this.getDirection());
    }

    // method for scan command 
    public String scan(){
        return command.scan();
    }

    // set direction of drone and write to JSON 
    public String setDirection(Compass direction){
        super.direction = direction;
        return command.heading(this.direction);
    }

    // method for echo using the drone 
    public String echo(String dir){
        // echo direction determined by method call paramter 
        if (dir.equals("F")){
            return command.echo(this.direction);
        }else if (dir.equals("R")){
            return command.echo(this.direction.right());
        }else{
            return command.echo(this. direction.left());
        }
        
    }

    // to string method for outputting 
    @Override
    public String toString(){
        return "Location: "+location.toString() + " Direction: "+this.direction;
    }

    // method to determine whether drone is currently on ground
    public boolean isGround(JSONObject response){
        return translator.ground(response);
    }

    //method to append stop comman to json and end mission 
    public String stop(){
        return command.stop();
    }

}