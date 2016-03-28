package be.uantwerpen.sc.models.security;

import java.util.List;

/**
 * Created by Thomas on 28/03/2016.
 */
public class Role
{
    private String name;
    private List<Permission> permissions;
    private List<User> users;

    public Role()
    {
        this.name = "";
        this.permissions = null;
    }

    public Role(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Permission> getPermissions()
    {
        return this.permissions;
    }

    public void setPermissions(List<Permission> permissions)
    {
        this.permissions = permissions;
    }
}
