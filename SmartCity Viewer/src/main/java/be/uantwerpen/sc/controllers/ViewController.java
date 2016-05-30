package be.uantwerpen.sc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Arthur on 17/03/2016.
 */
@Controller
public class ViewController
{
    @RequestMapping(value = {"/view"})
    public String showSimView(ModelMap model)
    {
        return "simView";
    }
}
