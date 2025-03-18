public class ExploreTurn implements ExplorerPhase{

    enum Steps{
        ECHOF,
        L,
        F,
        L2,
        L3,
        L4, 
        R1,
        R2,
        FG, 
        SCAN;
    }
    Drone d;

    public ExploreTurn(Drone d){
        this.d=d;
    }

    Steps step = Steps.ECHOF;
    Translator translator = new Translator();
    int groundDistance;

    public String getDecision(){

        switch(step){

            case ECHOF ->{
                return transaltor.echo(d.getdirection());
            }
            case L ->{
                d.left();
                
                return transaltor.heading()
            }
            case F ->{
                d.fly();
                return translator.fly();
            }
        }

            


    }

    public boolean getResponse(JSONObject response){
        switch(step){
            case Steps.ECHOF -> {
                step= Steps.L11;
                
            }
            case L1->{
                if(start==Compass.N or start==Compass.E){
                    d.left();
                }
                else   
                    d.right();
                return translator.heading(drone.getDirection());
            }

        }


    } 

    
}