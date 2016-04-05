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
 * Created by Thomas on 03/04/2016.
 */
@Service
@Profile("dev")
public class DatabaseLoaderDevelopment
{
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public DatabaseLoaderDevelopment(PermissionRepository permissionRepos, RoleRepository roleRepos, UserRepository userRepos)
    {
        this.permissionRepository = permissionRepos;
        this.roleRepository = roleRepos;
        this.userRepository = userRepos;
    }

    @PostConstruct
    private void initDatabase()
    {
        //Check if tables are initialised or empty
        if(permissionRepository.findAll().iterator().hasNext())
        {
            //Tables are initialised, no need to refill database
            return;
        }

        //Initialise user database
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

        //Test users
        User user1 = new User("Thomas", "Huybrechts", "thomas.huybrechts", "test");
        User user2 = new User("Arthur", "Janssens", "arthur.janssens", "test");
        User user3 = new User("Dennis", "Joosens", "dennis.joosens", "test");
        User user4 = new User("Niels", "Vervliet", "niels.vervliet", "test");

        //Set test users
        user1.setRoles(roles);
        user2.setRoles(roles);
        user3.setRoles(roles);
        user4.setRoles(roles);

        //Save users to database
        userRepository.save(adminUser);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
    }
}
