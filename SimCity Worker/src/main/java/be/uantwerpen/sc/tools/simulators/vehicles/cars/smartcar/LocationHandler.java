package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar;

/**
 * Created by Thomas on 28/05/2016.
 */
public class LocationHandler
{
    private int currentLocation;

    private LocationHandler()
    {
        this.currentLocation = 0;
    }

    public LocationHandler(int startLocation)
    {
        this.currentLocation = startLocation;
    }


}
