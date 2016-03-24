package be.uantwerpen.sc.models;

import javax.persistence.*;

/**
 * Created by Niels on 23/03/2016.
 */
@Entity
@Table(name = "Robot", schema = "", catalog = "smartcity")
public class RobotEntity {
    private int rid;
    private String state;
    private int linkId;
    private Integer percentageCompleted;
    private int jobId;

    @Id
    @Column(name = "RID")
    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    @Basic
    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "LINK_ID")
    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }

    @Basic
    @Column(name = "PERCENTAGE_COMPLETED")
    public Integer getPercentageCompleted() {
        return percentageCompleted;
    }

    public void setPercentageCompleted(Integer percentageCompleted) {
        this.percentageCompleted = percentageCompleted;
    }

    @Basic
    @Column(name = "JOB_ID")
    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RobotEntity that = (RobotEntity) o;

        if (rid != that.rid) return false;
        if (linkId != that.linkId) return false;
        if (jobId != that.jobId) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (percentageCompleted != null ? !percentageCompleted.equals(that.percentageCompleted) : that.percentageCompleted != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rid;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + linkId;
        result = 31 * result + (percentageCompleted != null ? percentageCompleted.hashCode() : 0);
        result = 31 * result + jobId;
        return result;
    }
}
