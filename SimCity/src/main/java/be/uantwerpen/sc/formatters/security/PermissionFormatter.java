package be.uantwerpen.sc.formatters.security;

import be.uantwerpen.sc.models.security.Permission;
import be.uantwerpen.sc.repositories.security.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Thomas on 04/04/2016.
 */
@Component
public class PermissionFormatter implements Formatter<Permission>
{
    @Autowired
    private PermissionRepository permissionRepository;

    public Permission parse(final String text, final Locale locale) throws ParseException
    {
        if(text != null && !text.isEmpty())
        {
            return permissionRepository.findOne(new Long(text));
        }
        else
        {
            return null;
        }
    }

    public String print(final Permission object, final Locale locale)
    {
        return (object != null ? object.getId().toString() : "");
    }
}

