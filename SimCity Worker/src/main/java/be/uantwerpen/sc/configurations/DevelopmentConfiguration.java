package be.uantwerpen.sc.configurations;

import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Created by Thomas on 25/02/2016.
 */
@Profile("dev")
@Configuration
@Import(EmbeddedServletContainerAutoConfiguration.class)
public class DevelopmentConfiguration
{
    //Development Configurations
}
