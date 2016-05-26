package be.uantwerpen.sc.services.security;

import be.uantwerpen.sc.models.security.User;
import be.uantwerpen.sc.repositories.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 04/04/2016.
 */
@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService securityService;

    public Iterable<User> findAll()
    {
        return this.userRepository.findAll();
    }

    public boolean add(final User user)
    {
        if(isDuplicatedUsername(user))
        {
            return false;
        }

        if(this.userRepository.save(user) != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean save(User user)
    {
        for(User u : findAll())
        {
            if(u.getId().equals(user.getId()))
            {
                if(!this.isDuplicatedUsername(user))
                {
                    u.setFirstName(user.getFirstName());
                    u.setLastName(user.getLastName());
                    u.setUsername(user.getUsername());
                    u.setPassword(user.getPassword());
                    u.setRoles(user.getRoles());

                    if(userRepository.save(u) != null)
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean delete(String username)
    {
        User u = findByUsername(username);

        if(u != null)
        {
            this.userRepository.delete(u.getId());

            return true;
        }

        return false;
    }

    public User findByUsername(String userName)
    {
        return userRepository.findByUsername(userName);
    }

    //Get logged in user
    public User getPrincipalUser()
    {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for(User u : findAll())
        {
            if(u.getUsername().compareTo(user.getUsername()) == 0)
            {
                return u;
            }
        }

        return null;
    }

    //Set logged in user
    public void setPrincipalUser(User user)
    {
        UserDetails newUserDetails = securityService.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUserDetails, newUserDetails.getPassword(), newUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public boolean usernameAlreadyExists(final String username)
    {
        List<User> users = userRepository.findAll();

        for(User userIt : users)
        {
            if(userIt.getUsername().equals(username))
            {
                return true;
            }
        }

        return false;
    }

    private boolean isDuplicatedUsername(final User user)
    {
        List<User> users = userRepository.findAll();

        for(User userIt : users)
        {
            if(userIt.getUsername().equals(user.getUsername()) && !userIt.getId().equals(user.getId()))
            {
                //Two different user objects with the same username
                return true;
            }
        }

        return false;
    }
}
