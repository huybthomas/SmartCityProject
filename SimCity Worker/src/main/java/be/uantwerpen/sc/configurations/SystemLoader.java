package be.uantwerpen.sc.configurations;

import be.uantwerpen.sc.services.sockets.SimCCommandHandler;
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
    @Autowired
    SimCCommandHandler simCCommandHandler;

    //Run after Spring context initialization
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        new Thread(simCCommandHandler).start();
        terminalService.systemReady();
    }
}
