package be.uantwerpen.sc.models.sim;

/**
 * Created by Thomas on 26/02/2016.
 */
public abstract class SimVehicle extends SimBot
{
    protected int startPoint;
    protected long simSpeed;

    public SimVehicle(String name, int startPoint, long simSpeed)
    {
        super(name);

        this.type = "vehicle";

        this.startPoint = startPoint;
        this.simSpeed = simSpeed;
    }

    public void setStartPoint(int startPoint)
    {
        this.startPoint = startPoint;
    }

    public void setSimSpeed(int simSpeed)
    {
        this.simSpeed = simSpeed;
    }

    @Override
    public boolean parseProperty(String property, String value) throws Exception
    {
        if(super.parseProperty(property, value))
        {
            return true;
        }

        switch(property.toLowerCase().trim())
        {
            case "speed":
                try
                {
                    int speed = parseInteger(value);

                    this.setSimSpeed(speed);

                    return true;
                }
                catch(Exception e)
                {
                    throw new Exception("Could not parse value for speed setting! " + e.getMessage());
                }
            case "startpoint":
                try
                {
                    int startPoint = parseInteger(value);

                    this.setStartPoint(startPoint);

                    return true;
                }
                catch(Exception e)
                {
                    throw new Exception("Could not parse value for start point setting! " + e.getMessage());
                }
            default:
                return false;
        }
    }

    private int parseInteger(String value) throws Exception
    {
        int parsedInt;

        try
        {
            parsedInt = Integer.parseInt(value);
        }
        catch(NumberFormatException e)
        {
            throw new Exception("'" + value + "' is not an integer value!");
        }

        return parsedInt;
    }
}
