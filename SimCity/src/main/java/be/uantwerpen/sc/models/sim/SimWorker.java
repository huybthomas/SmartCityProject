package be.uantwerpen.sc.models.sim;

import be.uantwerpen.sc.models.MyAbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Thomas on 03/04/2016.
 */
@Entity
@Table(name = "worker", schema = "", catalog = "simcity")
public class SimWorker extends MyAbstractPersistable<Long>
{
    private URL serverURL;
    private Date recordTime;
    private String workerName;
    private List<SimBot> bots;

    public SimWorker()
    {
        this.serverURL = null;
        this.recordTime = null;
        this.workerName = "";
        this.bots = null;
    }

}
