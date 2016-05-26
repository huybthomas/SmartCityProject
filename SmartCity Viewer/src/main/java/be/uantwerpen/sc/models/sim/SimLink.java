package be.uantwerpen.sc.models.sim;

/**
 * Created by Thomas on 27/02/2016.
 */
public class SimLink {

    SimPoint start, end;
    double length;

    public SimLink(SimPoint start, SimPoint end){
        this.start = start;
        this.end = end;
    }

    public SimPoint getEnd() {return end;}
    public void setEnd(SimPoint end) {this.end = end;}

    public SimPoint getStart() {return start;}
    public void setStart(SimPoint start) {this.start = start;}

    public double getLength() {return length;}
    public void setLength(double length) {this.length = length;}
}
