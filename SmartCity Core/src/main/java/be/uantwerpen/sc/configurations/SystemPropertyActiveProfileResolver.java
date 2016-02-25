package be.uantwerpen.sc.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfilesResolver;
import org.springframework.test.context.support.DefaultActiveProfilesResolver;

/**
 * Created by Thomas on 25/02/2016.
 */
@Configuration
public class SystemPropertyActiveProfileResolver implements ActiveProfilesResolver
{
    private final DefaultActiveProfilesResolver defaultActiveProfilesResolver = new DefaultActiveProfilesResolver();

    @Override
    public String[] resolve(Class<?> runClass)
    {
        if(System.getProperties().containsKey("spring.profiles.active"))
        {
            final String profiles = System.getProperty("spring.profiles.active");

            return profiles.split("\\s*, \\s*");
        }
        else
        {
            return defaultActiveProfilesResolver.resolve(runClass);
        }
    }

}
