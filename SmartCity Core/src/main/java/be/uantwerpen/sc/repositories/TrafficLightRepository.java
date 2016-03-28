package be.uantwerpen.sc.repositories;

import be.uantwerpen.sc.models.TrafficlightEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Niels on 26/03/2016.
 */
@Repository
public interface TrafficLightRepository extends CrudRepository<TrafficlightEntity,Integer> {
    List<TrafficlightEntity> findAll();
}
