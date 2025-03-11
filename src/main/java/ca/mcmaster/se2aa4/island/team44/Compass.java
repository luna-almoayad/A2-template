package ca.mcmaster.se2aa4.island.team44;

public class Compass{


    Direction d;
    public Compass( Direction d) {
        this.d = d;
    }


    public String right()
    {
        if(this.d == Direction.N) this.d = Direction.E;

        else if(this.d == Direction.E) this.d = Direction.S;

         else if(this.d== Direction.S) this.d = Direction.W;

         else if(this.d==Direction.W) this.d = Direction.N;

         return d.toString();
    }

    public String left()
    {
        if(this.d == Direction.N)  this.d=  Direction.W;

        else if(this.d == Direction.E) this.d= Direction.N;

         else if(this.d== Direction.S) this.d= Direction.E;

         else if(this.d==Direction.W)  this.d= Direction.S;

        return d.toString();
    }


    
}