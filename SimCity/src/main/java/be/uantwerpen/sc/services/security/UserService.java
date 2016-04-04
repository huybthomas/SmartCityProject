package be.uantwerpen.sc.services.security;

import be.uantwerpen.sc.models.security.User;
import be.uantwerpen.sc.repositories.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

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

    public User findByUsername(String userName)
    {
        return userRepository.findByUsername(userName);
    }
}
