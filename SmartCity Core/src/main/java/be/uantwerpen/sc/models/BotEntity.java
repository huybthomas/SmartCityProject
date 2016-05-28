package be.uantwerpen.sc.models;

import javax.persistence.*;

/**
 * Created by Niels on 24/03/2016.
 */
@Entity
@Table(name = "bot", schema = "", catalog = "smartcitydb")
public class BotEntity
{
    private Long rid;
    private Integer jobId;
    private Integer percentageCompleted;
    private String state;
    private LinkEntity linkId;

    @Id
    @Column(name = "rid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    @Basic
    @Column(name = "job_id")
    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    @Basic
    @Column(name = "percentage_completed")
    public Integer getPercentageCompleted() {
        return percentageCompleted;
    }

    public void setPercentageCompleted(Integer percentageCompleted) {
        this.percentageCompleted = percentageCompleted;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BotEntity that = (BotEntity) o;

        if (rid != that.rid) return false;
        if (jobId != null ? !jobId.equals(that.jobId) : that.jobId != null) return false;
        if (percentageCompleted != null ? !percentageCompleted.equals(that.percentageCompleted) : that.percentageCompleted != null)
            return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int)(rid % Integer.MAX_VALUE);
        result = 31 * result + (jobId != null ? jobId.hashCode() : 0);
        result = 31 * result + (percentageCompleted != null ? percentageCompleted.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "link_id", referencedColumnName = "lid")
    public LinkEntity getLinkId() {
        return linkId;
    }

    public void setLinkId(LinkEntity linkId) {
        this.linkId = linkId;
    }
}
