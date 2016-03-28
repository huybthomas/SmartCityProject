package be.uantwerpen.sc.repositories;

import be.uantwerpen.sc.models.PointEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Thomas on 25/02/2016.
 */
@Repository
public interface PointRepository extends CrudRepository<PointEntity, Integer>
{
    List<PointEntity> findAll();
}
