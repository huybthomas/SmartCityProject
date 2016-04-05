package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.security.User;
import be.uantwerpen.sc.services.security.RoleService;
import be.uantwerpen.sc.services.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Thomas on 03/04/2016.
 */
@Controller
public class UsersController extends GlobalModelController
{
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @InitBinder
    private void allowFields(WebDataBinder webDataBinder)
    {
        webDataBinder.setAllowedFields("username", "firstName", "lastName", "password", "roles");
    }

    @RequestMapping(value="/users/{username}/", method=RequestMethod.GET)
    @PreAuthorize("hasRole('logon')")
    public String showUserForm(@PathVariable String username, HttpServletRequest request, ModelMap model)
    {
        User user = userService.findByUsername(username);

        if(user != null)
        {
            model.addAttribute("user", user);
            model.addAttribute("allRoles", roleService.findAll());
        }
        else
        {
            model.clear();
            return "redirect:/settings/users?errorUserNotFound";
        }

        return "protected/settings/forms/userForm";
    }

    @RequestMapping(value="/users/{username}/", method=RequestMethod.POST)
    @PreAuthorize("hasRole('logon')")
    public String postUser(@Validated @ModelAttribute("user") User user, BindingResult result, HttpServletRequest request, SessionStatus sessionStatus, ModelMap model)
    {
        if(result.hasErrors())
        {
            return "protected/settings/forms/userForm";
        }

        if(userService.save(user))
        {
            //Check if logged in user has been edited
            if(userService.getPrincipalUser() == null)
            {
                userService.setPrincipalUser(user);
            }

            return "redirect:/settings/users?userEdited";
        }
        else
        {
            return "redirect:" + request.getRequestURI() + "?errorAlreadyExists";
        }
    }

    @RequestMapping(value="/users/{username}/delete")
    @PreAuthorize("hasRole('logon')")
    public String deleteUser(@PathVariable String username, HttpServletRequest request, ModelMap model)
    {
        if(userService.delete(username))
        {
            model.clear();
            return "redirect:/settings/users?userRemoved";
        }
        else
        {
            return "redirect:/settings/users?errorUserRemove";
        }
    }
}
