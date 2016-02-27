package be.uantwerpen.sc.models.sim;

import be.uantwerpen.sc.models.core.Point;

/**
 * Created by Thomas on 26/02/2016.
 */
public class SimCar extends SimVehicle
{
    public SimCar(String name, Point startPoint, long simSpeed)
    {
        super(name, startPoint, simSpeed);

        this.type = "car";
    }

    @Override
    protected void simulationProcess()
    {

    }
}
