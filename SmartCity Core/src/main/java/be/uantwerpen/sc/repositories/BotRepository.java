package be.uantwerpen.sc.repositories;

import be.uantwerpen.sc.models.BotEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Niels on 16/03/2016.
 */
@Repository
public interface BotRepository extends CrudRepository<BotEntity, Long>
{
    List<BotEntity> findAll();

}
