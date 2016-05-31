package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.handlers;

/**
 * Created by Thomas on 31/05/2016.
 */
public class Event
{
    private EventType type;
    private String property;
    private Object value;

    public enum EventType
    {
        DRIVE_EVENT
    }

    protected Event()
    {
        type = null;
        property = "";
        value = "";
    }

    public Event(EventType type, String property, Object value)
    {
        this.type = type;
        this.property = property;
        this.value = value;
    }

    public EventType getType()
    {
        return this.type;
    }

    public String getProperty()
    {
        return this.property;
    }

    public Object getValue()
    {
        return this.value;
    }
}
