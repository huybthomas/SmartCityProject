package be.uantwerpen.sc.repositories;

import be.uantwerpen.sc.models.Link;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Thomas on 27/02/2016.
 */
@Repository
public interface LinkRepository extends CrudRepository<Link, Long>
{
    List<Link> findAll();
}
