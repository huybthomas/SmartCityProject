package be.uantwerpen.sc.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Thomas on 26/02/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTests
{
    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @Before
    public void setup()
    {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    public void testViewHomepage() throws Exception
    {
        mockMvc.perform(get("/")).andExpect(view().name("public/homepage"));
    }

    @Test
    public void testViewAboutpage() throws Exception
    {
        mockMvc.perform(get("/about")).andExpect(view().name("public/about"));
    }
}
