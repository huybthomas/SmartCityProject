package be.uantwerpen.sc.models;

import javax.persistence.*;

/**
 * Created by Niels on 24/03/2016.
 */
@Entity
@Table(name = "point", schema = "", catalog = "smartcitydb")
public class Point
{
    private Long id;
    private String rfid;
    private String type;
    private int pointLock;

    @Id
    @Column(name = "pid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Basic
    @Column(name = "rfid")
    public String getRfid()
    {
        return rfid;
    }

    public void setRfid(String rfid)
    {
        this.rfid = rfid;
    }

    @Basic
    @Column(name = "type")
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Point that = (Point) o;

        if(id != that.id) return false;
        if(rfid != null ? !rfid.equals(that.rfid) : that.rfid != null) return false;
        if(type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int)(id % Integer.MAX_VALUE);

        result = 31 * result + (rfid != null ? rfid.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);

        return result;
    }

    @Basic
    @Column(name = "pointlock")
    public int getPointLock()
    {
        return pointLock;
    }

    public void setPointLock(int pointLock)
    {
        this.pointLock = pointLock;
    }

    @Override
    public String toString()
    {
        return "PointEntity{" +
                "id=" + id +
                ", rfid='" + rfid + '\'' +
                ", type='" + type + '\'' +
                ", pointLock=" + pointLock +
                '}';
    }
}
