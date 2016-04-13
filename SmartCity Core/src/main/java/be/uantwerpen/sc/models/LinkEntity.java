package be.uantwerpen.sc.models;

import javax.persistence.*;

/**
 * Created by Niels on 24/03/2016.
 */
@Entity
@Table(name = "link", schema = "", catalog = "smartcity")
public class LinkEntity {
    private int lid;
    private Integer length;
    private String startDirection;
    private String stopDirection;
    private PointEntity startId;
    private PointEntity stopId;

    @Id
    @Column(name = "lid")
    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    @Basic
    @Column(name = "lengte")
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Basic
    @Column(name = "start_direction")
    public String getStartDirection() {
        return startDirection;
    }

    public void setStartDirection(String startDirection) {
        this.startDirection = startDirection;
    }

    @Basic
    @Column(name = "stop_direction")
    public String getStopDirection() {
        return stopDirection;
    }

    public void setStopDirection(String stopDirection) {
        this.stopDirection = stopDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkEntity that = (LinkEntity) o;

        if (lid != that.lid) return false;
        if (length != null ? !length.equals(that.length) : that.length != null) return false;
        if (startDirection != null ? !startDirection.equals(that.startDirection) : that.startDirection != null)
            return false;
        if (stopDirection != null ? !stopDirection.equals(that.stopDirection) : that.stopDirection != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lid;
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (startDirection != null ? startDirection.hashCode() : 0);
        result = 31 * result + (stopDirection != null ? stopDirection.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "start_id", referencedColumnName = "pid")
    public PointEntity getStartId() {
        return startId;
    }

    public void setStartId(PointEntity startId) {
        this.startId = startId;
    }

    @OneToOne
    @JoinColumn(name = "stop_id", referencedColumnName = "pid")
    public PointEntity getStopId() {
        return stopId;
    }

    public void setStopId(PointEntity stopId) {
        this.stopId = stopId;
    }
}
