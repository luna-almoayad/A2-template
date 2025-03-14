package ca.mcmaster.se2aa4.island.team44;


public class Battery {
    private int currentBudget;
    //maybe make safe budget to go back 

    //I dont know if you want a constructor
    public Battery (int budget){
        this.currentBudget=budget;

    }

    public void useBudget(int cost){
        this.currentBudget -= cost;
    }

    public int getCurrentBudget(){
        return this.currentBudget;
    }

}
