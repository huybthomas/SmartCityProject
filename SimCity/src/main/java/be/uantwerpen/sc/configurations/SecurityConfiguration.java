package be.uantwerpen.sc.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by Thomas on 27/02/2016.
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        //Permit access to H2 console --Development only
        http.authorizeRequests().antMatchers("/h2console/**")
                                    .permitAll();

        http.authorizeRequests().antMatchers("/webjars/**")
                                    .permitAll();

        http.authorizeRequests().antMatchers("/login","/about")
                                    .permitAll()
                                    .anyRequest()
                                    .fullyAuthenticated()
                                    .and()
                                .formLogin()
                                    .loginPage("/login")
                                    .failureUrl("/login?error")
                                    .and()
                                .logout()
                                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                    .and()
                                .exceptionHandling()
                                    .accessDeniedPage("/access?accessdenied");

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Configuration
    protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter
    {
        @Autowired
        private UserDetailsService securityService;

        @Override
        public void init(AuthenticationManagerBuilder authBuilder) throws Exception
        {
            authBuilder.userDetailsService(securityService);
        }
    }
}
