package ca.mcmaster.se2aa4.island.team44;

public class EmergencySite extends POI {

    Location location;
    String id;

    public EmergencySite(String id, Location location){
       this.location = location;
       this.id = id;
    }

    public void setLocation(Location location){
        this.location= location;
   }

   public Location getLocation(){
    return this.location;
   }

   public String getID(){
    return this.id;
   }
}


