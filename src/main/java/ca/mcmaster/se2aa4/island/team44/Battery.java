package ca.mcmaster.se2aa4.island.team44;

public class Battery {
    private int currentBudget;
    private int sufficientBudget= 100; 

    public Battery (int budget){
        this.currentBudget=budget;
    }

    public void useBudget(int cost){
        this.currentBudget -= cost;
    }

    public int getCurrentBudget(){
        return this.currentBudget;
    }

    public boolean sufficientBattery(){
        return currentBudget >= sufficientBudget; 
    }

}
