package be.uantwerpen.sc.models.sim;

import be.uantwerpen.sc.models.MyAbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by Thomas on 03/04/2016.
 */
@Entity
@Table(name = "worker", schema = "", catalog = "simcity")
public class SimWorker extends MyAbstractPersistable<Long>
{
    private long workerID;
    private String workerName;
    private URL serverURL;
    private Date recordTime;

    @Transient
    private List<SimBot> bots;

    public SimWorker()
    {
        this.workerID = 0L;
        this.workerName = "";
        this.serverURL = null;
        this.recordTime = null;
        this.bots = null;
    }

    public void setWorkerId(long id)
    {
        this.workerID = id;
    }

    public long getWorkerId()
    {
        return this.workerID;
    }

    public void setWorkerName(String workerName)
    {
        this.workerName = workerName;
    }

    public String getWorkerName()
    {
        return this.workerName;
    }

    public void setServerURL(URL serverURL)
    {
        this.serverURL = serverURL;
    }

    public URL getServerURL()
    {
        return this.serverURL;
    }

    public void setRecordTime(Date recordTime)
    {
        this.recordTime = recordTime;
    }

    public Date getRecordTime()
    {
        return this.recordTime;
    }

    public void setBotList(List<SimBot> bots)
    {
        this.bots = bots;
    }

    public List<SimBot> getBotList()
    {
        return this.bots;
    }
}
