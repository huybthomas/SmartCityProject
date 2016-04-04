package be.uantwerpen.sc.data;

import be.uantwerpen.sc.models.security.Permission;
import be.uantwerpen.sc.models.security.Role;
import be.uantwerpen.sc.models.security.User;
import be.uantwerpen.sc.repositories.security.PermissionRepository;
import be.uantwerpen.sc.repositories.security.RoleRepository;
import be.uantwerpen.sc.repositories.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Thomas on 04/04/2016.
 */
@Service
@Profile({"default","standalone"})
public class DatabaseLoader
{
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public DatabaseLoader(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository)
    {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void initDatabase()
    {
        //Check if tables are initialized or empty
        if(permissionRepository.findAll().iterator().hasNext())
        {
            //Tables are initialized, no need to refill database
            return;
        }

        //Initialize user database
        initUserDatabase();
    }

    private void initUserDatabase()
    {
        List<Permission> allPermissions = new ArrayList<Permission>();
        List<Role> roles;

        //Default permissions
        allPermissions.add(new Permission("logon"));

        //Save permission to database
        Iterator<Permission> it = allPermissions.iterator();
        while(it.hasNext())
        {
            permissionRepository.save(it.next());
        }

        //Default roles
        Role administrator = new Role("Administrator");

        //Set administrator
        administrator.setPermissions(allPermissions);

        //Save roles to database
        roleRepository.save(administrator);

        //Default users
        User adminUser = new User("Admin", "", "admin", "admin");

        //Set adminUser
        roles = new ArrayList<Role>();
        roles.add(administrator);
        adminUser.setRoles(roles);

        //Save users to database
        userRepository.save(adminUser);
    }
}
