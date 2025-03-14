package ca.mcmaster.se2aa4.island.team44;

public class Creeks{

    private String id; 
    private Location location;
    public Creeks (String id, Location location){
        this.id=id;
        this.location=location;
    }
    public Location getLocation(){
        return location; 
    }

    public String getID(){
        return this.id; 
    }




        
}

