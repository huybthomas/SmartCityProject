package be.uantwerpen.sc.repositories.security;

import be.uantwerpen.sc.models.security.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Thomas on 04/04/2016.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
    List<User> findByLastName(String lastName);

    User findByUsername(String username);

    List<User> findAll();
}
