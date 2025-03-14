package ca.mcmaster.se2aa4.island.team44;

public class Creeks extends POI{

    private String id; 
    public Creeks (Location location, String id){
        super (location);
        this.id=id;
    }

    public String getID(){
        return this.id; 
    }
        
}

