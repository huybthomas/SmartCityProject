package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.services.sim.SimWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Thomas on 03/04/2016.
 */
@RestController
public class WorkerController extends GlobalModelController
{
    @Autowired
    private SimWorkerService workerService;

    @RequestMapping(value="/workers/{workerName}/delete")
    @PreAuthorize("hasRole('logon')")
    public String deleteUser(@PathVariable String workerName, HttpServletRequest request, ModelMap model)
    {
        if(workerService.delete(workerName))
        {
            model.clear();
            return "redirect:/settings/workers?workerRemoved";
        }
        else
        {
            return "redirect:/settings/workers?errorWorkerRemove";
        }
    }

}
