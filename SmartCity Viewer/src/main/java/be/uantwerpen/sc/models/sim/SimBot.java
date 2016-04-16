package be.uantwerpen.sc.models.sim;

/**
 * Created by Thomas on 27/02/2016.
 */
public class SimBot {

    int linkID;
    double percentageComplete;  //from 0-1
    int[] loc;

    public SimBot(){
        linkID = -1;
        loc = new int[]{0,0};
        percentageComplete = 0;
    }

    public void update(SimPath currentPath){
        int size = currentPath.getLocs().size();
        int selected = (int)(size*percentageComplete);
        loc = currentPath.getLocs().get(selected);
    }

    public void add10percent(){
        this.percentageComplete += 0.1;
        if(percentageComplete >= 1){
            percentageComplete = 0.99;
        }
    }

    public void setLinkID(int linkID){
        this.linkID = linkID;
    }

    public double getPercentageComplete(){return percentageComplete;}
    public int[] getLoc() {
        return loc;
    }

}
