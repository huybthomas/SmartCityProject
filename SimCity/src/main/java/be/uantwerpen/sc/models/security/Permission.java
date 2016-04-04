package be.uantwerpen.sc.models.security;

import be.uantwerpen.sc.models.MyAbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Thomas on 04/04/2016.
 */
@Entity
@Table(name = "permission", schema = "", catalog = "sc_security")
public class Permission extends MyAbstractPersistable<Long>
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
