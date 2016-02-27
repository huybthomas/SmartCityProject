package be.uantwerpen.sc.models.core.messages;

import be.uantwerpen.sc.models.core.Position;

/**
 * Created by Thomas on 26/02/2016.
 */
public class VehicleStatus
{
    private final long id;
    private final long vehicleId;
    private final String status;
    private final Position position;

    public VehicleStatus(long id, long vehicleId, String status, Position position)
    {
        this.id = id;
        this.vehicleId = vehicleId;
        this.status = status;
        this.position = position;
    }

    public long getId()
    {
        return this.id;
    }

    public long getVehicleId()
    {
        return this.vehicleId;
    }

    public String getStatus()
    {
        return this.status;
    }

    public Position getPosition()
    {
        return this.position;
    }
}
