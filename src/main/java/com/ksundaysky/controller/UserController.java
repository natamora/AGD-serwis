package com.ksundaysky.controller;

import com.ksundaysky.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

public class UserController {

    @RequestMapping(value = {"/admin/user/create"}, method = RequestMethod.GET)
    public ModelAndView createNewUser() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/user/create");
        return modelAndView;
    }

}
