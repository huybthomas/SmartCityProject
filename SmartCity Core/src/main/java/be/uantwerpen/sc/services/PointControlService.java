package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.PointEntity;
import be.uantwerpen.sc.repositories.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Niels on 3/04/2016.
 */
@Service
public class PointControlService {
    @Autowired
    private PointRepository pointRepository;

    public List<PointEntity> getAllPoints(){return pointRepository.findAll();}

    public PointEntity getPoint(int id){return  pointRepository.findOne(id);}
}
