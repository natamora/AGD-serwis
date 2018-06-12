package com.ksundaysky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Controller
class CalendarController {

    @Autowired
    UserController userController;

    @RequestMapping(value="/calendar", method=RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("calendar");
    }

    @RequestMapping(value="/calendar2", method=RequestMethod.GET)
    public ModelAndView index2() {
        return new ModelAndView("calendar2");
    }

    @RequestMapping(value="/usersCalendar", method=RequestMethod.GET)
    public ModelAndView index3() {
        return new ModelAndView("usersCalendar");
    }


}
