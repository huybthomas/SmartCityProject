package be.uantwerpen.sc.models;

import java.util.List;

/**
 * Created by Niels on 26/03/2016.
 */
public class Map {

    private List<PointEntity>puntList;
    private List<LinkEntity> linkList;
    private List<TrafficlightEntity> trafficLightList;

    public Map() {
    }

    public List<PointEntity> getPuntList() {return puntList;}

    public void setPuntList(List<PointEntity> puntList) {this.puntList = puntList;}

    public void addPuntToList(PointEntity punt){
        puntList.add(punt);
    }

    public List<LinkEntity> getLinkList() {return linkList;}

    public void setLinkList(List<LinkEntity> linkList) {this.linkList = linkList;}

    public void addLinkToList(LinkEntity link){
        linkList.add(link);
    }

    public List<TrafficlightEntity> getTrafficLightList() {return trafficLightList;}

    public void setTrafficLightList(List<TrafficlightEntity> trafficLightList) {this.trafficLightList = trafficLightList;}

    public void addTrafficLightToList(TrafficlightEntity trafficLight){
        trafficLightList.add(trafficLight);
    }
}
