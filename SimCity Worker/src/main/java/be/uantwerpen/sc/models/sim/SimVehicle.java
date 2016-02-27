package be.uantwerpen.sc.models.sim;

import be.uantwerpen.sc.models.core.Point;

/**
 * Created by Thomas on 26/02/2016.
 */
public abstract class SimVehicle extends SimBot
{
    protected Point startPoint;
    protected long simSpeed;

    public SimVehicle(String name, Point startPoint, long simSpeed)
    {
        super(name);

        this.type = "vehicle";

        this.startPoint = startPoint;
        this.simSpeed = simSpeed;
    }

    public void setStartPoint(Point startPoint)
    {
        this.startPoint = startPoint;
    }

    public void setSimSpeed(int simSpeed)
    {
        this.simSpeed = simSpeed;
    }
}
