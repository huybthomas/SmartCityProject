package be.uantwerpen.sc.models;

import javax.persistence.*;

/**
 * Created by Niels on 24/03/2016.
 */
@Entity
@Table(name = "bot", schema = "", catalog = "smartcitydb")
public class Bot
{
    private Long id;
    private Long jobId;
    private Long travelledDistance;
    private Integer percentageCompleted;
    private String state;
    private Link linkId;

    @Id
    @Column(name = "rid")
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
    @Column(name = "job_id")
    public Long getJobId()
    {
        return jobId;
    }

    public void setJobId(Long jobId)
    {
        this.jobId = jobId;
    }

    @Basic
    @Column(name = "percentage_completed")
    public Integer getPercentageCompleted()
    {
        return percentageCompleted;
    }

    public void setPercentageCompleted(Integer percentageCompleted)
    {
        this.percentageCompleted = percentageCompleted;
    }

    @Basic
    @Column(name = "state")
    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Bot that = (Bot) o;

        if(id != that.id) return false;
        if(jobId != null ? !jobId.equals(that.jobId) : that.jobId != null) return false;
        if(percentageCompleted != null ? !percentageCompleted.equals(that.percentageCompleted) : that.percentageCompleted != null)
            return false;
        if(state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = (int)(id % Integer.MAX_VALUE);

        result = 31 * result + (jobId != null ? jobId.hashCode() : 0);
        result = 31 * result + (percentageCompleted != null ? percentageCompleted.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);

        return result;
    }

    @OneToOne
    @JoinColumn(name = "link_id", referencedColumnName = "lid")
    public Link getLinkId()
    {
        return linkId;
    }

    public void setLinkId(Link linkId)
    {
        this.linkId = linkId;
    }
}
