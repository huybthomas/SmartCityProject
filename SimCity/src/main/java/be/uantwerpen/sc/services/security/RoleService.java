package be.uantwerpen.sc.services.security;

import be.uantwerpen.sc.models.security.Role;
import be.uantwerpen.sc.repositories.security.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Thomas on 04/04/2016.
 */
@Service
public class RoleService
{
    @Autowired
    private RoleRepository roleRepository;

    public Iterable<Role> findAll()
    {
        return this.roleRepository.findAll();
    }
}
