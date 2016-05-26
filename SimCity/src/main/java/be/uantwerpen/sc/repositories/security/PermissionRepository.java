package be.uantwerpen.sc.repositories.security;

import be.uantwerpen.sc.models.security.Permission;
import be.uantwerpen.sc.models.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Thomas on 04/04/2016.
 */
@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long>
{
    @Query(value="select p from User u left join u.roles r left join r.permissions p where u=:usr")
    Iterable<Permission> findAllForUser(@Param("usr") User user);

    Iterable<Permission> findByName(String permission);
}
