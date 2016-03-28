package be.uantwerpen.sc.models.security;

/**
 * Created by Thomas on 28/03/2016.
 */
public class Permission
{
    private String name;

    public Permission()
    {
        this.name = "";
    }

    public Permission(String name)
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

        Permission permission = (Permission) object;

        return this.name.equals(permission.getName());
    }
}
