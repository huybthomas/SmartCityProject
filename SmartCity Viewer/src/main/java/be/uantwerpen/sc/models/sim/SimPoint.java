package be.uantwerpen.sc.models.sim;

/**
 * Created by Thomas on 27/02/2016.
 */
public class SimPoint
{
    private int id;
    private int posX, posY;
    private String rfId;

    public SimPoint()
    {
        this.id = -1;
        this.posX = 0;
        this.posY = 0;
        this.rfId = "";
    }

    public SimPoint(int id)
    {
        this.id = id;
        this.posX = 0;
        this.posY = 0;
        this.rfId = "";
    }

    public SimPoint(int id, int posX, int posY)
    {
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.rfId = "";
    }

    public void up(){this.posY++;}
    public void right(){this.posX++;}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!SimPoint.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final SimPoint other = (SimPoint) obj;
        if ((this.id == -1) ? (other.id != -1) : !(this.id == other.id)) {
            return false;
        }
        return true;
    }

    public int getId()
    {
        return this.id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public void setPos(int posX, int posY){this.posX = posX; this.posY = posY;}

    public int getPosX()
    {
        return this.posX;
    }
    public void setPosX(int posX)
    {
        this.posX = posX;
    }

    public int getPosY()
    {
        return this.posY;
    }
    public void setPosY(int posY)
    {
        this.posY = posY;
    }

    public String getRfId()
    {
        return this.rfId;
    }
    public void setRfId(String rfId)
    {
        this.rfId = rfId;
    }
}
