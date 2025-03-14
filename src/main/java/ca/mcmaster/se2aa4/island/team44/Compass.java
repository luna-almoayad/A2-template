package ca.mcmaster.se2aa4.island.team44;
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

    public Compass opposite(){
        switch(this){
            case N: return S;
            case E: return W;
            case W: return E;
            case S: return N;
            default: return this;
        }

    }
    

}