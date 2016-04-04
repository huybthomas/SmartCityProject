package be.uantwerpen.sc.repositories.security;

import be.uantwerpen.sc.SimCityApplication;
import be.uantwerpen.sc.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.sc.models.security.Permission;
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
public class PermissionRepositoryTests
{
    @Autowired
    private PermissionRepository permissionRepository;

    @Test
    public void testSavePermission()
    {
        //Get repository size before test
        int origPermissionRepositorySize = (int)permissionRepository.count();

        //Setup permission
        Permission permission = new Permission();
        permission.setName("TestPermission");

        //Save permission, verify has ID value after save
        assertNull(permission.getId());       //Null before save
        permissionRepository.save(permission);
        assertNotNull(permission.getId());    //Not null after save

        //Fetch from database
        Permission fetchedPermission = permissionRepository.findOne(permission.getId());

        //Should not be null
        assertNotNull(fetchedPermission);

        //Should equals
        assertEquals(permission.getId(), fetchedPermission.getId());
        assertEquals(permission.getName(), fetchedPermission.getName());

        //Update description and save
        fetchedPermission.setName("NewPermissionName");
        permissionRepository.save(fetchedPermission);

        //Get from database, should be updated
        Permission fetchedUpdatedPermission = permissionRepository.findOne(fetchedPermission.getId());
        assertEquals(fetchedPermission.getName(), fetchedUpdatedPermission.getName());

        //Verify count of permission in database
        long permissionCount = permissionRepository.count();
        assertEquals(permissionCount, origPermissionRepositorySize + 1);        //One permission has been added to the database

        //Get all permission, list should only have one more then initial value
        Iterable<Permission> permissions = permissionRepository.findAll();

        int count = 0;

        for(Permission p : permissions)
        {
            count++;
        }

        //There are originally 'origPermissionRepositorySize' permissions declared in the database (+1 has been added in this test)
        assertEquals(count, origPermissionRepositorySize + 1);
    }

    @Test
    public void testDeletePermission()
    {
        //Get repository size before test
        int origPermissionRepositorySize = (int)permissionRepository.count();

        //Setup permission
        Permission permission = new Permission();
        permission.setName("TestPermission");

        //Save permission, verify if it has ID value after save
        assertNull(permission.getId());       //Null before save
        permissionRepository.save(permission);
        assertNotNull(permission.getId());    //Not null after save

        //Verify count of permissions in database
        long permissionCount = permissionRepository.count();
        Assert.assertEquals(permissionCount, origPermissionRepositorySize + 1);      //One permission has been added to the database

        //Fetch from database
        Permission fetchedPermission = permissionRepository.findOne(permission.getId());

        //Should not be null
        assertNotNull(fetchedPermission);

        //Delete permission from database
        permissionRepository.delete(fetchedPermission.getId());

        //Fetch from database (should not exist anymore)
        fetchedPermission = permissionRepository.findOne(permission.getId());

        //Should be null
        assertNull(fetchedPermission);

        //Verify count of permission in database
        permissionCount = permissionRepository.count();
        assertEquals(permissionCount, origPermissionRepositorySize);        //One permission has been deleted rom the database

        //Get all permission, list should the same amount than the initial value
        Iterable<Permission> permissions = permissionRepository.findAll();

        int count = 0;

        for(Permission p : permissions)
        {
            count++;
        }

        //There are originally 'origPermissionRepositorySize' permissions declared in the database
        assertEquals(count, origPermissionRepositorySize);
    }
}
