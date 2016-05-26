package be.uantwerpen.sc.formatters.security;

import be.uantwerpen.sc.models.security.User;
import be.uantwerpen.sc.repositories.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Thomas on 04/04/2016.
 */
@Component
public class UserFormatter implements Formatter<User>
{
    @Autowired
    private UserRepository userRepository;

    public User parse(final String text, final Locale locale) throws ParseException
    {
        if(text != null && !text.isEmpty())
        {
            return userRepository.findOne(new Long(text));
        }
        else
        {
            return null;
        }
    }

    public String print(final User object, final Locale locale)
    {
        return (object != null ? object.getId().toString() : "");
    }
}
