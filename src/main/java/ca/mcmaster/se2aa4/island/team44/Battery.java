package ca.mcmaster.se2aa4.island.team44;

import org.json.JSONObject;

public class Battery {
    private int startBudget;
    private int currentBudget;
    //maybe make safe budget to go back 

    //I dont know if you want a constructor
    public Battery(int battery){
        this.startBudget = battery;
    }

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
