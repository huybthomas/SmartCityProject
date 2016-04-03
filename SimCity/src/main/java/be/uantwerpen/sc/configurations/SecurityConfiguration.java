package be.uantwerpen.sc.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Thomas on 27/02/2016.
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().antMatchers("/webjars/**")
                                    .permitAll();

        http.authorizeRequests().antMatchers("/**")
                                    .permitAll()
                                    .anyRequest()
                                    .fullyAuthenticated();
    }
}
