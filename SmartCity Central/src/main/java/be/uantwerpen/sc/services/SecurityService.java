package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.security.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.StreamSupport;

/**
 * Created by Thomas on 28/03/2016.
 */
@Service
public class SecurityService implements UserDetailsService
{
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        UserDetails userDetails;

        User user = null;

        if(user != null)
        {
            Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            //authorities.addAll(StreamSupport.stream());

            userDetails = new org.springframework.security.core.userdetails.User(userName, user.getPassword(), true, true, true, true, authorities);
        }
        else
        {
            throw new UsernameNotFoundException("No user with username '" + userName + "' found!");
        }

        return userDetails;
    }
}
