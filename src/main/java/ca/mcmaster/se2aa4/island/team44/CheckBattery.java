package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONObject;

public class CheckBattery {
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
