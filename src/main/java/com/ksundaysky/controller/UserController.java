package com.ksundaysky.controller;

import com.ksundaysky.model.Role;
import com.ksundaysky.model.User;
import com.ksundaysky.service.RoleService;
import com.ksundaysky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = {"/users/create"}, method = RequestMethod.GET)
    public ModelAndView createNewUser() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        int roleId = 0;
        modelAndView.addObject("user", user);
        modelAndView.addObject("user.role_id_int", roleId);
        modelAndView.setViewName("/users/create");
        List<Role> roleList = roleService.findAll();
        modelAndView.addObject("role_map", roleList);

        return modelAndView;
    }


    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Ten email został już przypisany do pracownika");
        }
        if (bindingResult.hasErrors()) {
            List<Role> roleList = roleService.findAll();
            modelAndView.addObject("role_map", roleList);
            modelAndView.setViewName("/users/create");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Pracownik został dodany");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/home");

        }
        return modelAndView;
    }
    @RequestMapping(value = "/users/edit/{id}")
    public ModelAndView editUser(@PathVariable int id){
        User user = userService.findUserById(id);



    @RequestMapping(value="/users/update", method=RequestMethod.POST)
    public ModelAndView updateUser(@Valid User user, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserById(user.getId());
        if (userExists != null) {
            userExists.setEmail(user.getEmail());
            userExists.setRoles(user.getRoles());
            userExists.setName(user.getName());
            userExists.setLastName(user.getLastName());
            userService.updateUser(userExists);
        }
        modelAndView.addObject("successMessage", "Pracownik został dodany");
        modelAndView.addObject("user",new User());
        modelAndView.setViewName("/home");

        return modelAndView;
    }
}
