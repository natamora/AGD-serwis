package com.ksundaysky.controller;

import com.ksundaysky.model.User;
import com.ksundaysky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = {"/users/create"}, method = RequestMethod.GET)
    public ModelAndView createNewUser() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("/users/create");
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
            modelAndView.setViewName("/users/create");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Pracownik został dodany");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("/home");

        }
        return modelAndView;
    }

}
