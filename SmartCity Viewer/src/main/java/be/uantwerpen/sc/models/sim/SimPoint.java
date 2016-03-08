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
        this.id = 0;
        this.posX = 0;
        this.posY = 0;
        this.rfId = "";
    }

    public int getId()
    {
        return this.id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

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
