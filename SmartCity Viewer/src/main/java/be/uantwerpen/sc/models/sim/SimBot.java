package be.uantwerpen.sc.models.sim;

/**
 * Created by Thomas on 27/02/2016.
 */
public class SimBot {

    double[] loc;

    public SimBot(){
        loc = new double[2];
        loc[0] = 20;
        loc[1] = 20;
    }

    public double[] getLoc() {
        return loc;
    }

    public void setLoc(double[] loc) {
        this.loc = loc;
    }

}
