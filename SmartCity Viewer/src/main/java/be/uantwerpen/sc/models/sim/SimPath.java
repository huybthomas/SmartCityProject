package be.uantwerpen.sc.models.sim;

import be.uantwerpen.sc.models.LinkEntity;

import java.util.ArrayList;

/**
 * Created by Arthur on 16/04/2016.
 */
public class SimPath {

    private int linkID;
    private int length;
    private ArrayList<int[]> locs;

    public SimPath(int linkID){
        this.linkID = linkID;
        locs = new ArrayList<>();
    }

    public void addLoc(int x, int y){
        int loc[] = {x,y};
        locs.add(loc);
    }

    public int getLinkID(){return linkID;}
    public ArrayList<int[]> getLocs(){
        return locs;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void up(){
        for(int[] loc : locs){
            loc[1]++;
        }

    }

    public void right(){
        for(int[] loc : locs){
            loc[0]++;
        }
    }
}
