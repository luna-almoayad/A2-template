package ca.mcmaster.se2aa4.island.team44;


//Chnage because the dron reads char
public enum Compass{
    N,
    E, 
    S, 
    W;


    public Compass right(){
        switch (this){
            case N: return E;
            case E: return S;
            case S: return W;
            case W: return N; 
            default: return this;
        }
    }

    public Compass left(){
         switch (this){
            case N: return W;
            case E: return N;
            case S: return E;
            case W: return S; 
            default: return this;
        }
    
    }
    

}