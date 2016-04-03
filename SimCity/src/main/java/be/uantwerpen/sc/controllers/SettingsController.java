package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.sim.SimWorker;
import be.uantwerpen.sc.services.SimWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Thomas on 03/04/2016.
 */
@Controller
public class SettingsController extends GlobalModelController
{
    @Autowired
    private SimWorkerService simWorkerService;

    @RequestMapping(value = {"/settings", "/settings/server"})
    public String showServerSettings(ModelMap model)
    {
        return "protected/settings/serverSettings";
    }

    @RequestMapping(value = {"/settings/users"})
    public String showUsersSettings(ModelMap model)
    {
        return "protected/settings/usersSettings";
    }

    @RequestMapping(value = {"/settings/workers"})
    public String showWorkersSettings(ModelMap model)
    {
        Iterable<SimWorker> workers = simWorkerService.findAll();

        model.addAttribute("allWorkers", workers);

        return "protected/settings/workersSettings";
    }
}
