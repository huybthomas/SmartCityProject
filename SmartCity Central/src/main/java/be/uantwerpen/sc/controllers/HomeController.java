package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.tools.Terminal;
import be.uantwerpen.sc.tools.DevelopersList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by Thomas on 25/02/2016.
 */
@Controller
public class HomeController
{
    @Autowired
    DevelopersList developersList;

    @RequestMapping(value = {"/"})
    public String showHomepage(ModelMap model)
    {
        return "public/homepage";
    }

    @RequestMapping(value = {"/about"})
    public String showAboutpage(ModelMap model)
    {
        try
        {
            List<String> supervisors = developersList.getSupervisorDevelopers();
            model.addAttribute("supervisors", supervisors);

            Map<String, List<String>> students = developersList.getStudentDevelopers();
            model.addAttribute("students", students);
        }
        catch(Exception e)
        {
            Terminal.printTerminalError(e.getMessage());
        }

        return "public/about";
    }
}
