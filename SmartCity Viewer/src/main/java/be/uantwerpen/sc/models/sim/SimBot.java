package be.uantwerpen.sc.models.sim;

import be.uantwerpen.sc.models.Bot;

/**
 * Created by Thomas on 27/02/2016.
 */
public class SimBot extends Bot{

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
