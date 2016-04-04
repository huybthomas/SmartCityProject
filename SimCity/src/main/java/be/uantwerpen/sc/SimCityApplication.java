package be.uantwerpen.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication(exclude = {EmbeddedServletContainerAutoConfiguration.class})
public class SimCityApplication extends SpringBootServletInitializer
{
	public static void main(String[] args)
	{
		SpringApplication.run(SimCityApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder)
	{
		return applicationBuilder.sources(SimCityApplication.class);
	}
}
