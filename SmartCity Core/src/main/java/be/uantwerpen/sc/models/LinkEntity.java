package be.uantwerpen.sc.models;

import javax.persistence.*;

/**
 * Created by Niels on 23/03/2016.
 */
@Entity
@Table(name = "Link", schema = "", catalog = "smartcity")
public class LinkEntity {
    private int lid;

    @OneToOne
    @PrimaryKeyJoinColumn
    private PuntEntity startId;
    private Integer lengte;
    private String startDirection;
    private String stopDirection;

    @Id
    @Column(name = "LID")
    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public PuntEntity getStartId() {
        return startId;
    }

    public void setStartId(PuntEntity puntEntity) {
        this.startId = puntEntity;
    }

    @Basic
    @Column(name = "LENGTE")
    public Integer getLengte() {
        return lengte;
    }

    public void setLengte(Integer lengte) {
        this.lengte = lengte;
    }

    @Basic
    @Column(name = "START_DIRECTION")
    public String getStartDirection() {
        return startDirection;
    }

    public void setStartDirection(String startDirection) {
        this.startDirection = startDirection;
    }

    @Basic
    @Column(name = "STOP_DIRECTION")
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
        if (lengte != null ? !lengte.equals(that.lengte) : that.lengte != null) return false;
        if (startDirection != null ? !startDirection.equals(that.startDirection) : that.startDirection != null)
            return false;
        if (stopDirection != null ? !stopDirection.equals(that.stopDirection) : that.stopDirection != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lid;
        result = 31 * result + (lengte != null ? lengte.hashCode() : 0);
        result = 31 * result + (startDirection != null ? startDirection.hashCode() : 0);
        result = 31 * result + (stopDirection != null ? stopDirection.hashCode() : 0);
        return result;
    }
}
