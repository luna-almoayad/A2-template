package ca.mcmaster.se2aa4.island.team44;

public class EmergencySite extends POI {

    /*HashMap<Integer, Location> minPerimeter;
    HashMap<Integer, Location> maxPerimeter;

    ArrayList<Location> perimeter;*/

    Location location;
    String id;

    public EmergencySite(String id, Location location){
        /*
        minPerimeter= new HashMap<Integer, Location>();
        maxPerimeter= new HashMap<Integer, Location>();
        perimeter= new ArrayList<Location>();
        */
       this.location = location;
       this.id = id;
    }
/*
    public void addLocation(Location location){ //add as perimeter, and maybe hash each x-nums
        if(perimeter.get(location.getX())>location.getX())
            minpPerimeter.put(location.getX(), location);
        else if(perimeter.get(location.getX())<location.getY())
            maxPerimeter.put(location.getX(), location);
        else{
            minPerimeter.put(location.getX(), location);
            maxPerimeter.put(location.getX(), location);
        }
    }
    */
   public void setLocation(Location location){
        this.location= location;
   }

   public Location getLocation(){
    return this.location;
   }


    
}
