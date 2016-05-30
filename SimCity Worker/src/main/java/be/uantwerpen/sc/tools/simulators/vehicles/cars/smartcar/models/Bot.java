package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.models;

import be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.models.map.Link;

/**
 * Created by Niels on 24/03/2016.
 */
public class Bot
{
    private Long rid;
    private Integer jobId;
    private Integer percentageCompleted;
    private String state;
    private Link linkId;

    public Long getRid()
    {
        return rid;
    }

    public void setRid(Long rid)
    {
        this.rid = rid;
    }

    public Integer getJobId()
    {
        return jobId;
    }

    public void setJobId(Integer jobId)
    {
        this.jobId = jobId;
    }

    public Integer getPercentageCompleted()
    {
        return percentageCompleted;
    }

    public void setPercentageCompleted(Integer percentageCompleted)
    {
        this.percentageCompleted = percentageCompleted;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public Link getLinkId()
    {
        return linkId;
    }

    public void setLinkId(Link linkId)
    {
        this.linkId = linkId;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Bot that = (Bot) o;

        if(rid != that.rid)
            return false;

        if(jobId != null ? !jobId.equals(that.jobId) : that.jobId != null)
            return false;

        if(percentageCompleted != null ? !percentageCompleted.equals(that.percentageCompleted) : that.percentageCompleted != null)
            return false;

        if(state != null ? !state.equals(that.state) : that.state != null)
            return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int)(rid % Integer.MAX_VALUE);

        result = 31 * result + (jobId != null ? jobId.hashCode() : 0);
        result = 31 * result + (percentageCompleted != null ? percentageCompleted.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);

        return result;
    }
}
