package ca.mcmaster.se2aa4.island.team44.site;

import ca.mcmaster.se2aa4.island.team44.navigation.Location;
public class EmergencySite{

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

   public String toString(){
      return this.location.toString();
   }
}


