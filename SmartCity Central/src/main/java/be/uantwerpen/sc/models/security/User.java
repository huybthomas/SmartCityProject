package be.uantwerpen.sc.models.security;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 28/03/2016.
 */
public class User
{
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private List<Role> roles;

    public User()
    {
        this.firstName = "";
        this.lastName = "";
        this.userName = "";
        this.password = "";

        this.roles = new ArrayList<Role>();
    }

    public User(String firstName, String lastName, String userName, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
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

    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
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

        return this.userName.equals(user.getUserName());
    }
}
