package be.uantwerpen.sc.services.security;

import be.uantwerpen.sc.models.security.User;
import be.uantwerpen.sc.repositories.security.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Thomas on 04/04/2016.
 */
@Service
public class SecurityService implements UserDetailsService
{
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserDetails userDetails;

        User user = userService.findByUsername(username);

        if(user != null)
        {
            Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            authorities.addAll(StreamSupport.stream(permissionRepository.findAllForUser(user).spliterator(), false).map(permission -> new SimpleGrantedAuthority("ROLE_" + permission.getName())).collect(Collectors.toList()));

            userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
        }
        else
        {
            throw new UsernameNotFoundException("No user with username '" + username + "' found!");
        }

        return userDetails;
    }
}
