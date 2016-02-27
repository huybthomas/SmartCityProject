package be.uantwerpen.sc.models.core;

import be.uantwerpen.sc.models.MyAbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Thomas on 25/02/2016.
 */
@Entity
public class Point extends MyAbstractPersistable<Long>
{
    @NotNull
    private String rfId;

    protected Point()
    {
        this.rfId = "";
    }

    public Point(String rfId)
    {
        this.rfId = rfId;
    }

    public String getRfId()
    {
        return this.rfId;
    }

    public void setRfId(String rfId)
    {
        this.rfId = rfId;
    }

    @Override
    public boolean equals(Object object)
    {
        if(this == object)
        {
            return true;
        }

        if(object == null || this.getClass() != object.getClass())
        {
            return false;
        }

        Point point = (Point) object;

        return this.rfId.equals(point.getRfId());
    }
}
