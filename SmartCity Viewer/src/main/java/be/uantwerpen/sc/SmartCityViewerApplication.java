package be.uantwerpen.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@SpringBootApplication(exclude = {EmbeddedServletContainerAutoConfiguration.class})
public class SmartCityViewerApplication extends SpringBootServletInitializer
{
	public static void main(String[] args)
	{
		SpringApplication.run(SmartCityViewerApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder)
	{
		return applicationBuilder.sources(SmartCityViewerApplication.class);
	}
}
