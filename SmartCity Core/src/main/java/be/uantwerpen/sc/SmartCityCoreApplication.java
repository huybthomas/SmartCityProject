package be.uantwerpen.sc;

import be.uantwerpen.sc.repositories.LinkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@SpringBootApplication(exclude = {EmbeddedServletContainerAutoConfiguration.class})
public class SmartCityCoreApplication extends SpringBootServletInitializer
{
	public static void main(String[] args)
	{
		SpringApplication.run(SmartCityCoreApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder)
	{
		return applicationBuilder.sources(SmartCityCoreApplication.class);
	}

	@Bean
	CommandLineRunner init(final LinkRepository linkRepository){

		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {

			}
		};
	}
}
