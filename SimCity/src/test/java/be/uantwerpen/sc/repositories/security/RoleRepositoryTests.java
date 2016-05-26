package be.uantwerpen.sc.repositories.security;

import be.uantwerpen.sc.SimCityApplication;
import be.uantwerpen.sc.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.sc.models.security.Role;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

/**
 * Created by Thomas on 04/04/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SimCityApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
@WebAppConfiguration
public class RoleRepositoryTests
{
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSaveRole()
    {
        //Get repository size before test
        int origRoleRepositorySize = (int)roleRepository.count();

        //Setup role
        Role role = new Role();
        role.setName("TestRole");

        //Save role, verify has ID value after save
        assertNull(role.getId());       //Null before save
        roleRepository.save(role);
        assertNotNull(role.getId());    //Not null after save

        //Fetch from database
        Role fetchedRole = roleRepository.findOne(role.getId());

        //Should not be null
        assertNotNull(fetchedRole);

        //Should equals
        assertEquals(role.getId(), fetchedRole.getId());
        assertEquals(role, fetchedRole);

        //Update description and save
        fetchedRole.setName("NewRoleName");
        roleRepository.save(fetchedRole);

        //Get from database, should be updated
        Role fetchedUpdatedRole = roleRepository.findOne(fetchedRole.getId());
        assertEquals(fetchedRole.getName(), fetchedUpdatedRole.getName());

        //Verify count of roles in database
        long roleCount = roleRepository.count();
        assertEquals(roleCount, origRoleRepositorySize + 1);        //One role has been added to the database

        //Get all roles, list should only have one more then initial value
        Iterable<Role> roles = roleRepository.findAll();

        int count = 0;

        for(Role p : roles)
        {
            count++;
        }

        //There are originally 'origRoleRepositorySize' roles declared in the database (+1 has been added in this test)
        assertEquals(count, origRoleRepositorySize + 1);
    }

    @Test
    public void testDeleteRole()
    {
        //Get repository size before test
        int origRoleRepositorySize = (int)roleRepository.count();

        //Setup role
        Role role = new Role();
        role.setName("TestRole");

        //Save role, verify has ID value after save
        assertNull(role.getId());       //Null before save
        roleRepository.save(role);
        assertNotNull(role.getId());    //Not null after save

        //Verify count of roles in database
        long roleCount = roleRepository.count();
        Assert.assertEquals(roleCount, origRoleRepositorySize + 1);      //One role has been added to the database

        //Fetch from database
        Role fetchedRole = roleRepository.findOne(role.getId());

        //Should not be null
        assertNotNull(fetchedRole);

        //Delete role from database
        roleRepository.delete(fetchedRole.getId());

        //Fetch from database (should not exist anymore)
        fetchedRole = roleRepository.findOne(role.getId());

        //Should be null
        assertNull(fetchedRole);

        //Verify count of roles in database
        roleCount = roleRepository.count();
        Assert.assertEquals(roleCount, origRoleRepositorySize);          //One role has been deleted from the database

        //Get all roles, list should have the same amount than the initial value
        Iterable<Role> roles = roleRepository.findAll();

        int count = 0;

        for(Role r : roles)
        {
            count++;
        }

        //There are originally 'origRoleRepositorySize' roles declared in the database
        Assert.assertEquals(count, origRoleRepositorySize);
    }
}
