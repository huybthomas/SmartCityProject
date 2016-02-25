package be.uantwerpen.sc;

import be.uantwerpen.sc.configurations.SystemPropertyActiveProfileResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SmartCityCoreApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
public class SmartCityCoreApplicationTests
{
	@Test
	public void contextLoads()
	{
		//Loading basic context
	}

}
