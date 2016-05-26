package be.uantwerpen.sc.repositories.security;

import be.uantwerpen.sc.models.security.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Thomas on 04/04/2016.
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long>
{
    Iterable<Role> findByName(String role);
}
