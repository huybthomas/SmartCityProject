package be.uantwerpen.sc.repositories;

import be.uantwerpen.sc.models.PuntEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Thomas on 25/02/2016.
 */
@Repository
public interface PointRepository extends CrudRepository<PuntEntity, Integer>
{
    List<PuntEntity> findAll();
}
