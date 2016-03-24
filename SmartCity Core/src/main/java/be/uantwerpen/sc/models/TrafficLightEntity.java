package be.uantwerpen.sc.models;

import javax.persistence.*;

/**
 * Created by Niels on 23/03/2016.
 */
@Entity
@Table(name = "TrafficLight", schema = "", catalog = "smartcity")
public class TrafficLightEntity {
    private int tlid;
    private String state;
    private int puntId;
    private String direction;

    @Id
    @Column(name = "TLID")
    public int getTlid() {
        return tlid;
    }

    public void setTlid(int tlid) {
        this.tlid = tlid;
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
    @Column(name = "PUNT_ID")
    public int getPuntId() {
        return puntId;
    }

    public void setPuntId(int puntId) {
        this.puntId = puntId;
    }

    @Basic
    @Column(name = "DIRECTION")
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrafficLightEntity that = (TrafficLightEntity) o;

        if (tlid != that.tlid) return false;
        if (puntId != that.puntId) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (direction != null ? !direction.equals(that.direction) : that.direction != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tlid;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + puntId;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }
}
