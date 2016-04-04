package be.uantwerpen.sc.repositories.security;

import be.uantwerpen.sc.SimCityApplication;
import be.uantwerpen.sc.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.sc.models.security.User;
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
public class UserRepositoryTests
{
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveBot()
    {
        //Get repository size before test
        int origUserRepositorySize = (int) userRepository.count();

        //Setup user
        User user = new User();
        user.setUsername("TestUserName");
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setPassword("test");

        //Save user, verify has ID value after save
        assertNull(user.getId());       //Null before save
        userRepository.save(user);
        assertNotNull(user.getId());    //Not null after save

        //Fetch from database
        User fetchedUser = userRepository.findOne(user.getId());

        //Should not be null
        assertNotNull(fetchedUser);

        //Should equals
        assertEquals(user.getId(), fetchedUser.getId());
        assertEquals(user, fetchedUser);

        //Update description and save
        fetchedUser.setUsername("NewTestUserName");
        userRepository.save(fetchedUser);

        //Get from database, should be updated
        User fetchedUpdatedUser = userRepository.findOne(fetchedUser.getId());
        assertEquals(fetchedUser.getUsername(), fetchedUpdatedUser.getUsername());

        //Verify count of users in database
        long userCount = userRepository.count();
        assertEquals(userCount, origUserRepositorySize + 1);        //One user has been added to the database

        //Get all users, list should only have one more then initial value
        Iterable<User> users = userRepository.findAll();

        int count = 0;

        for (User p : users)
        {
            count++;
        }

        //There are originally 'origUserRepositorySize' users declared in the database (+1 has been added in this test)
        assertEquals(count, origUserRepositorySize + 1);
    }

    @Test
    public void testDeleteUser()
    {
        //Get repository size before test
        int origUserRepositorySize = (int)userRepository.count();

        //Setup user
        User user = new User();
        user.setUsername("TestDeleteName");
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setPassword("test");

        //Save user, verify has ID value after save
        assertNull(user.getId());       //Null before save
        userRepository.save(user);
        assertNotNull(user.getId());    //Not null after save

        //Verify count of users in database
        long userCount = userRepository.count();
        Assert.assertEquals(userCount, origUserRepositorySize + 1);      //One user has been added to the database

        //Fetch from database
        User fetchedUser = userRepository.findOne(user.getId());

        //Should not be null
        assertNotNull(fetchedUser);

        //Delete user from database
        userRepository.delete(fetchedUser.getId());

        //Fetch from database (should not exist anymore)
        fetchedUser = userRepository.findOne(user.getId());

        //Should be null
        assertNull(fetchedUser);

        //Verify count of users in database
        userCount = userRepository.count();
        Assert.assertEquals(userCount, origUserRepositorySize);          //One user has been deleted from the database

        //Get all users, list should have the same amount than the initial value
        Iterable<User> users = userRepository.findAll();

        int count = 0;

        for(User u : users)
        {
            count++;
        }

        //There are originally 'origUserRepositorySize' users declared in the database
        Assert.assertEquals(count, origUserRepositorySize);
    }
}
