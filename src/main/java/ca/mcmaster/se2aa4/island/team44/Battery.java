package ca.mcmaster.se2aa4.island.team44;

<<<<<<< HEAD:src/main/java/ca/mcmaster/se2aa4/island/team44/Battery.java
import org.json.JSONObject;

=======
>>>>>>> 3235dd9 (changes):src/main/java/ca/mcmaster/se2aa4/island/team44/CheckBattery.java
public class Battery {
    private int startBudget;
    private int currentBudget;
    //maybe make safe budget to go back 

    public void setBudget(int budget){
        this.startBudget= budget; 
        this.currentBudget= budget;
    }

    public void useBudget(int cost){
        this.currentBudget -= cost;
    }

    public int getCurrentBudget(){
        return this.currentBudget;
    }

}
