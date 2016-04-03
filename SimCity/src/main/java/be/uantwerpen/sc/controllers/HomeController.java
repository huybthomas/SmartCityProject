package be.uantwerpen.sc.controllers;

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
    public String showHomepage(ModelMap model)
    {
        return "public/homepage";
    }

    @RequestMapping(value = {"/about"})
    public String showAboutpage(ModelMap model)
    {
        return "public/about";
    }
}