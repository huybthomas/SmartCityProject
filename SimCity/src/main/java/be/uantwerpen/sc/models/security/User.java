package be.uantwerpen.sc.models.security;

import be.uantwerpen.sc.models.MyAbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 04/04/2016.
 */
@Entity
@Table(name = "user", schema = "", catalog = "sc_security")
public class User extends MyAbstractPersistable<Long>
{
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    @ManyToMany
    @JoinTable(
            name="USER_ROLE",
            schema = "",
            catalog = "sc_security",
            joinColumns = {
                    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles;

    public User()
    {
        this.firstName = "";
        this.lastName = "";
        this.username = "";
        this.password = "";

        this.roles = new ArrayList<Role>();
    }

    public User(String firstName, String lastName, String username, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;

        this.roles = new ArrayList<Role>();
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public List<Role> getRoles()
    {
        return this.roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    public boolean hasPermission(String permission)
    {
        for(Role r : roles)
        {
            for(Permission p : r.getPermissions())
            {
                if(p.getName().equals(permission))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object object)
    {
        if(this == object)
        {
            return true;
        }

        if(object == null || this.getClass() != object.getClass())
        {
            return false;
        }

        User user = (User) object;

        return this.username.equals(user.getUsername());
    }
}
