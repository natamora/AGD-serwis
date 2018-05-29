package com.ksundaysky.controller;

import org.springframework.stereotype.Controller;
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


    @RequestMapping(value="/calendar", method=RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("calendar");
    }

}
