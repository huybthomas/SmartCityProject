package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.SimJavaCore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niels on 12/05/2016.
 */
@Service
public class SimRobotService {

    List<SimJavaCore> simJavaCoreList;

    public SimRobotService() {
        this.simJavaCoreList = new ArrayList<>();
    }

    public void addSimJavaCore(SimJavaCore simJavaCore){
        this.simJavaCoreList.add(simJavaCore);
    }

    public List<SimJavaCore> getSimJavaCoreList(){
        return this.simJavaCoreList;
    }
}
