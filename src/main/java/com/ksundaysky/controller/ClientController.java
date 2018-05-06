package com.ksundaysky.controller;

import org.springframework.stereotype.Controller;

import com.ksundaysky.service.ClientService;
import com.ksundaysky.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientController {
    @Autowired
    ClientService clientService;

    @RequestMapping(value = {"/clients/create"}, method = RequestMethod.GET)
    public ModelAndView createNewProdct() {
        ModelAndView modelAndView = new ModelAndView();
        Client client = new Client();
        modelAndView.addObject("client", client);
        modelAndView.setViewName("/clients/create");
        return modelAndView;
    }
}
