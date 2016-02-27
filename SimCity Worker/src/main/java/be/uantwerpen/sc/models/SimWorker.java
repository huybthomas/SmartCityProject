package be.uantwerpen.sc.models;

/**
 * Created by Thomas on 27/02/2016.
 */
public class SimWorker
{
    private int id;
    private String name;

    public SimWorker()
    {
        this.id = 0;
        this.name = "worker";
    }

    public SimWorker(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
