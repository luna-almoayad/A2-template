package ca.mcmaster.se2aa4.island.team44.drones;

// Battery class responsible for tracking battery usage of drone 
public class Battery {
    private int currentBudget;
    //battery stash to safely return 
    private final int sufficientBudget= 100; 

    public Battery (int budget){
        this.currentBudget=budget;
    }

    //method to subtract cost from budget
    public void useBudget(int cost){
        this.currentBudget -= cost;
    }

    // method to retrice current budget 
    public int getCurrentBudget(){
        return this.currentBudget;
    }

    //method to determine if we have enough battery 
    public boolean sufficientBattery(){
        return currentBudget >= sufficientBudget; 
    }

}
