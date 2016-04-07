package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.security.User;
import be.uantwerpen.sc.models.sim.SimWorker;
import be.uantwerpen.sc.services.security.RoleService;
import be.uantwerpen.sc.services.security.UserService;
import be.uantwerpen.sc.services.sim.SimWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = {"/settings", "/settings/server"})
    @PreAuthorize("hasRole('logon')")
    public String showServerSettings(ModelMap model)
    {
        return "protected/settings/serverSettings";
    }

    @RequestMapping(value = {"/settings/users"})
    @PreAuthorize("hasRole('logon')")
    public String showUsersSettings(ModelMap model)
    {
        User user = new User();
        Iterable<User> users = userService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("allUsers", users);
        model.addAttribute("allRoles", roleService.findAll());

        return "protected/settings/usersSettings";
    }

    @RequestMapping(value = {"/settings/workers"})
    @PreAuthorize("hasRole('logon')")
    public String showWorkersSettings(ModelMap model)
    {
        SimWorker worker = new SimWorker();

        model.addAttribute("worker", worker);

        return "protected/settings/workersSettings";
    }
}
