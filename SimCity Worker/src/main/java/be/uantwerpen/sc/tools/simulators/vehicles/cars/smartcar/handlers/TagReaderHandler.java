package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.handlers;

/**
 * Created by Thomas on 29/05/2016.
 */
public class TagReaderHandler
{
    private LocationHandler locationHandler;

    protected TagReaderHandler()
    {
        this.locationHandler = null;
    }

    public TagReaderHandler(LocationHandler locationHandler)
    {
        this.locationHandler = locationHandler;
    }

    public String readTag()
    {
        String tag = null;

        if(locationHandler.onNode())
        {
            tag = locationHandler.getNodeRFID(locationHandler.getCurrentLocation());

            if(tag != null)
            {
                if(tag.equals(""))
                {
                    //No tag linked to node
                    tag = null;
                }
            }
        }

        if(tag != null)
        {
            return "TAG DETECTION EVENT: " + tag;
        }
        else
        {
            return "TAG DETECTION EVENT: NONE";
        }
    }
}
