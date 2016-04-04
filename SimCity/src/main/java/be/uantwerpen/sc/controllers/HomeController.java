package be.uantwerpen.sc.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Thomas on 27/02/2016.
 */
@Controller
public class HomeController extends GlobalModelController
{
    @RequestMapping(value = {"/"})
    @PreAuthorize("hasRole('logon')")
    public String showHomepage(ModelMap model)
    {
        return "protected/homepage";
    }

    @RequestMapping(value = {"/about"})
    public String showAboutpage(ModelMap model)
    {
        return "public/about";
    }
}