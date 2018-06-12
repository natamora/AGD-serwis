package com.ksundaysky.controller;

import com.ksundaysky.model.Log;
import com.ksundaysky.model.Role;
import com.ksundaysky.service.LogService;
import com.ksundaysky.service.RoleService;
import com.ksundaysky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class LogController {

    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam(name="type", required=true, defaultValue="all") String type, @RequestParam(name="roleId", required=false, defaultValue = "0") List<Integer> roleId, @RequestParam(name="date", required=false, defaultValue = "") String date)
    {

        ModelAndView modelAndView = new ModelAndView();
        List<Log> logs;
        if(type.equals("all")){
            logs = logService.findAll();
        }
        else{
            logs = logService.findAll().stream()
                    .filter(log -> log.getAction_type().equals(type.toLowerCase()))
                    .collect(Collectors.toList());
        }
       try{
           DateFormat formatter;
           formatter = new SimpleDateFormat("yyyy-MM-dd");
           Date dateTmp = formatter.parse(date);
           Timestamp timestampDate = new Timestamp(dateTmp.getTime());
           Calendar cal  = Calendar.getInstance();
           cal.setTime(timestampDate);
           cal.add(Calendar.DAY_OF_WEEK,1);
           Timestamp stopDate = new Timestamp(cal.getTime().getTime());
           logs = logs.stream()
                   .filter(log ->log.getTimestamp().after(timestampDate) && log.getTimestamp().before(stopDate))
                   .collect(Collectors.toList());
       } catch (ParseException e){

       }
        if(roleId.get(0)!=0){
            List<Log> tmp;
            List<Log> tmpCollect = new LinkedList<>();
            for(int role : roleId){
                tmp = logs.stream()
                        .filter(log ->userService.findUserByEmail(log.getAuthor_email()).getRole_id() == role)
                        .collect(Collectors.toList());
                tmpCollect.addAll(tmp);
            }
            logs=tmpCollect;
        }


        modelAndView.addObject("logs",logs);
        modelAndView.setViewName("/logs/index");
        modelAndView.addObject("roleId",new LinkedList<Integer>());
        List<Role> roles = roleService.findAll();
        modelAndView.addObject("roles",roles);
        return modelAndView;
    }
}
