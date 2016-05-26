package be.uantwerpen.sc.formatters.security;

import be.uantwerpen.sc.models.security.Role;
import be.uantwerpen.sc.repositories.security.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Thomas on 04/04/2016.
 */
@Component
public class RoleFormatter implements Formatter<Role>
{
    @Autowired
    private RoleRepository roleRepository;

    public Role parse(final String text, final Locale locale) throws ParseException
    {
        if(text != null && !text.isEmpty())
        {
            return roleRepository.findOne(new Long(text));
        }
        else
        {
            return null;
        }
    }

    public String print(final Role object, final Locale locale)
    {
        return (object != null ? object.getId().toString() : "");
    }
}
