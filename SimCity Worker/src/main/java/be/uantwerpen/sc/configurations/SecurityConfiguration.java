package be.uantwerpen.sc.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Thomas on 25/02/2016.
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    private Environment environment;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        //CONFIGURE SECURITY PARAMS
        devConfiguration(http);
    }

    protected void devConfiguration(HttpSecurity http) throws Exception
    {
        for(String profile : environment.getActiveProfiles())
        {
            if(profile.equals("dev"))
            {
                http.csrf().disable();
                http.headers().frameOptions().disable();

                return;
            }
        }
    }
}
