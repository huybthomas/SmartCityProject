package be.uantwerpen.sc.configurations;

import be.uantwerpen.sc.services.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by Thomas on 26/02/2016.
 */
@Configuration
public class SystemLoader implements ApplicationListener<ContextRefreshedEvent>
{
    @Autowired
    TerminalService terminalService;

    //Run after Spring context initialization
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        terminalService.systemReady();
    }
}
