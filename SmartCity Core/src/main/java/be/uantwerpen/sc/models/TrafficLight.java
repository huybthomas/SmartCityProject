package be.uantwerpen.sc.models;

import javax.persistence.*;

/**
 * Created by Niels on 24/03/2016.
 */
@Entity
@Table(name = "trafficlight", schema = "", catalog = "smartcitydb")
public class TrafficLight
{
    private int tlid;
    private String direction;
    private String state;
    private Point pointid;

    @Id
    @Column(name = "tlid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getTlid() {
        return tlid;
    }

    public void setTlid(int tlid) {
        this.tlid = tlid;
    }

    @Basic
    @Column(name = "direction")
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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

        TrafficLight that = (TrafficLight) o;

        if (tlid != that.tlid) return false;
        if (direction != null ? !direction.equals(that.direction) : that.direction != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tlid;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @OneToOne
    @JoinColumn(name = "punt_id", referencedColumnName = "pid")
    public Point getPointid() {
        return pointid;
    }

    public void setPointid(Point pointid) {
        this.pointid = pointid;
    }
}
