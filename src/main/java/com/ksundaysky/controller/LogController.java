package com.ksundaysky.controller;

import com.ksundaysky.model.Log;
import com.ksundaysky.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LogController {

    @Autowired
    private LogService logService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/logs")
    public ModelAndView index(HttpServletRequest request)
    {
        ModelAndView modelAndView = new ModelAndView();
        List<Log> logs = logService.findAll();
        modelAndView.addObject("logs",logs);
        modelAndView.setViewName("/logs/index");
        return modelAndView;
    }
}
