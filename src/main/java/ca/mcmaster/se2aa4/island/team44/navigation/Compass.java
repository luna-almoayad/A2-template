package ca.mcmaster.se2aa4.island.team44.navigation;

public enum Compass{
    N,
    E, 
    S, 
    W;

    public Compass right(){
        if (this == N){
            return E;
        }
        else if (this == E){
            return S;
        }
        else if (this == S){
            return W;
        }
        else if (this == W){
            return N;
        }
        else{
            return this;
        }
    }

    public Compass left(){
        if (this == N){
            return W;
        }
        else if (this == E){
            return N;
        }
        else if (this == S){
            return E;
        }
        else if (this == W){
            return S;
        }
        else{
            return this;
        }
    
    }

    @SuppressWarnings("ConvertToStringSwitch")
    public static Compass toEnum(String direction){
        if("N".equals(direction)){
            return N;
        } 
        else if ("E".equals(direction)){
            return E;
        }
        else if ("S".equals(direction)){
            return S;
        } 
        else if ("W".equals(direction)) {
            return W;
        }
        else{
            return  null;
        }  
    }
    
    

}