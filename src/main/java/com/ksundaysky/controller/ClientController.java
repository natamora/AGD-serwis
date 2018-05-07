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

import java.util.List;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ClientController {
    @Autowired
    ClientService clientService;

    @RequestMapping(value = {"/clients/create"}, method = RequestMethod.GET)
    public ModelAndView createNewClient() {
        ModelAndView modelAndView = new ModelAndView();
        Client client = new Client();
        modelAndView.addObject("client", client);
        modelAndView.setViewName("/clients/create");
        return modelAndView;
    }
    @RequestMapping(value = "/clients/create", method = RequestMethod.POST)
    public ModelAndView createNewClient(@Valid Client client, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/clients/create");
        }
        else {
            clientService.saveClient(client);
            modelAndView.addObject("successMessage", "Klient został dodany");
           // modelAndView.addObject("client", new Client());
            modelAndView.setViewName("/home");
        }


        return modelAndView;
    }

    @RequestMapping(value = "/clients")
    public ModelAndView index(HttpServletRequest request)
    {
        ModelAndView modelAndView = new ModelAndView();
        List<Client> clients = clientService.findAll();
        modelAndView.addObject("clients",clients);
        modelAndView.setViewName("/clients/index");
        String successMessage = (String)request.getSession().getAttribute("successMessage");
        if( successMessage != null) {
            modelAndView.addObject("successMessage", successMessage);
            request.getSession().removeAttribute("successMessage");
        }

        return modelAndView;
    }
}
