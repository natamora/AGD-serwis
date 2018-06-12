package com.ksundaysky.controller;

import com.ksundaysky.model.*;

import com.ksundaysky.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class AccountantController {


    @Autowired
    private VisitService visitService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ComponentService componentService;



    @RequestMapping(value = "/services")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        List<Visit> visits = visitService.findAll();
        for (Visit visit : visits) {
            setVisitData(visit);
        }
        modelAndView.addObject("visits", visits);
        modelAndView.setViewName("/services/index");
        String successMessage = (String) request.getSession().getAttribute("successMessage");
        if (successMessage != null) {
            modelAndView.addObject("successMessage", successMessage);
            request.getSession().removeAttribute("successMessage");
        }

        return modelAndView;
    }


    private void setVisitData(Visit visit) {
        //  Component component = componentService.findById(visit.getComponent_id());
        //    if(component != null )
        //      visit.setComponentName(component.getComponent_name());
        Client client = clientService.findById(visit.getClient_id());
        if (client != null) {
            String clientNameSurname = client.getClient_name() + ' ' + client.getClient_surname();
            visit.setClientNameSurname(clientNameSurname);
        }
        User serviceman = userService.findUserById(visit.getEmployerId());
        if (serviceman != null)
            visit.setServisantSurname(serviceman.getLastName());
        Product product = productService.findById(visit.getProduct_id());
        if (product != null)
            visit.setProductName(product.getProduct_name());
    }


   // private void sortDate() {
   //     List<Visit> findAllByOrderByDateAsc();
  //  }


 //   Sort sort = new Sort(Sort.Direction.DESC, "b.someColumn");


}