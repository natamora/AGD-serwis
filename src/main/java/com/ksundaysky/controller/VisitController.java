package com.ksundaysky.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksundaysky.model.*;
import com.ksundaysky.model.Product;
import com.ksundaysky.model.wrapper.VisitComponents;
import com.ksundaysky.repository.EventRepository;
import com.ksundaysky.repository.VisitRepository;
import com.ksundaysky.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

//@Controller
@RestController
public class VisitController {

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

    public VisitController() {
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','REJESTRUJACY')")
    @RequestMapping(value = {"/clients/{client_id}/products/{id}/visits/create"}, method = RequestMethod.GET)
    public ModelAndView createNewVisit(@PathVariable("id") int id, @PathVariable("client_id") int client_id) {
        ModelAndView modelAndView = new ModelAndView();
        Visit visit = new Visit();

        List<User> serwisantList = userService.findAll().stream()
                .filter(user -> user.getRole_id() == 2)
                .map(user -> new User(user.getId(), user.getEmail(), user.getPassword(), user.getName(), user.getLastName(), user.getActive(), user.getRole_id()))
                .collect(Collectors.toList());
        modelAndView.addObject("visit", visit);
        modelAndView.addObject("productId", id);
        modelAndView.addObject("client_id", client_id);
        modelAndView.addObject("serwisantList", serwisantList);
        modelAndView.setViewName("/clients/products/visits/create");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','REJESTRUJACY')")
    @RequestMapping(value = "/clients/{client_id}/products/{id}/visits/create", method = RequestMethod.POST)
    public ModelAndView createNewVisit(@Valid Visit visit, BindingResult bindingResult, @PathVariable int id, @PathVariable("client_id") int client_id) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            List<User> serwisantList = userService.findAll().stream()
                    .filter(user -> user.getRole_id() == 2)
                    .map(user -> new User(user.getId(), user.getEmail(), user.getPassword(), user.getName(), user.getLastName(), user.getActive(), user.getRole_id()))
                    .collect(Collectors.toList());
            modelAndView.addObject("visit", visit);
            modelAndView.addObject("productId", id);
            modelAndView.addObject("client_id", client_id);
            modelAndView.addObject("serwisantList", serwisantList);
            modelAndView.setViewName("/clients/products/visits/create");
        } else {
            visit.setClient_id(client_id);
            visit.setProduct_id(id);
            // visit.setReceipt_date(visit.getReceipt_date().replace("T"," "));
            //visit.setReceipt_date(visit.getReceipt_date()+" "+visit.time_start);
            // visit.receipt_date = visit.receipt_date+" "+visit.time_start;
            //System.out.println("*****************************"+visit.getReceipt_date()+"****************************");
            visitService.saveVisit(visit);
            modelAndView.addObject("successMessage", "Wizyta została dodana");
            //modelAndView.addObject("visit", new Visit());
            modelAndView.setViewName("/home");
        }


        return modelAndView;
    }

    @RequestMapping(value = "/visits")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        List<Visit> visits = visitService.findAll();
        for (Visit visit : visits) {
            setVisitData(visit);
        }
        modelAndView.addObject("visits", visits);
        modelAndView.setViewName("/clients/products/visits/index");
        String successMessage = (String) request.getSession().getAttribute("successMessage");
        if (successMessage != null) {
            modelAndView.addObject("successMessage", successMessage);
            request.getSession().removeAttribute("successMessage");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/eventss", method = RequestMethod.GET)
    public String getEventsInRange() {
//        List<Visit> visits = visitService.findAll();
//
//        List<Event> events = new ArrayList<Event>();
//
        String jsonMsg = null;
//        for(Visit visit : visits)
//        {
//            Event event = new Event();
//            event.setStart(visit.getReceipt_date_start());
//            event.setEnd(visit.getReceipt_date_end());
//            event.setTitle("dziaaaaala");
//            events.add(event);
//        }
//
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
        return jsonMsg;
    }

    @RequestMapping(value = "/clients/{client_id}/products/{product_id}/visits/{id}")
    public ModelAndView show(@PathVariable int id) {
        Visit visit = visitService.findVisitById(id);

        ModelAndView modelAndView = new ModelAndView();
        if (visit == null) {
            modelAndView.addObject("errorMessage", "Wizyta o danym id nie istnieje");
        }
        modelAndView.addObject("visit", visit);
        modelAndView.setViewName("/clients/products/visits/show");

        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SERWISANT')")
    @RequestMapping(value = "/clients/{client_id}/products/{product_id}/visits/repair/{id}")
    public ModelAndView repair(@PathVariable("product_id") int product_id, @PathVariable("client_id") int client_id, @PathVariable("id") Integer id) {

        ModelAndView modelAndView = new ModelAndView();
        Visit visit = visitService.findVisitById(id);

        visit.receipt_date_end = visit.receipt_date_end.substring(0, 16);
        visit.receipt_date_start = visit.receipt_date_start.substring(0, 16);

        if (visit.getPick_up_date() != null)
            visit.setPick_up_date(visit.getPick_up_date().substring(0, 16));
        if (visit.getRepair_date() != null)
            visit.setRepair_date(visit.getRepair_date().substring(0, 16));

        if (visit == null) {
            modelAndView.addObject("errorMessage", "Wizyta o danym id nie istnieje");
        }
        List<Component> componentList = componentService.findAll();

        VisitComponents visitComponents = new VisitComponents(visit, componentList);

        modelAndView.addObject("visit", visit);
        modelAndView.addObject("componentList", componentList);
        modelAndView.addObject("visitComponents", visitComponents);
        modelAndView.addObject("errorMessageRepair"," ");
        modelAndView.addObject("errorMessagePick", " ");

        modelAndView.setViewName("/clients/products/visits/repair");
        return modelAndView;

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SERWISANT')")
    @RequestMapping(value = "/clients/{client_id}/products/{product_id}/visits/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute VisitComponents visitComponents, BindingResult bindingResult, @PathVariable int product_id, @PathVariable("client_id") int client_id) {

        ModelAndView modelAndView = new ModelAndView();
        Visit visit = visitComponents.getVisit();
        Visit visitExists = visitService.findVisitById(visit.getId());
        visitExists.receipt_date_end = visitExists.receipt_date_end.substring(0, 16);
        visitExists.receipt_date_start = visitExists.receipt_date_start.substring(0, 16);

        if (visitExists != null) {
            visitExists.setActual_description(visit.getActual_description());
            if(!visit.getRepair_date().matches( "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}") || !visit.getPick_up_date().matches("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}")){
                if(!visit.getRepair_date().matches( "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}")) {
                    modelAndView.addObject("errorMessageRepair", "Ustaw prawidłową datę! Wg wzoru: yyyy-MM-dd HH:mm");
                }
                if(!visit.getPick_up_date().matches( "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}")){
                    modelAndView.addObject("errorMessagePick", "Ustaw prawidłową datę! Wg wzoru: yyyy-MM-dd HH:mm");
                }

                List<Component> componentList = componentService.findAll();

                modelAndView.addObject("visit", visit);
                modelAndView.addObject("componentList", componentList);
                modelAndView.addObject("visitComponents", visitComponents);
                modelAndView.setViewName("/clients/products/visits/repair");

                return modelAndView;
            }
            visitExists.setRepair_date(visit.getRepair_date());
            visitExists.setPick_up_date(visit.getPick_up_date());
            visitExists.setCosts(visit.getCosts());
            visitExists.setNote(visit.getNote());
            if (visitComponents.getComponents().size() > 0) {

                Set<Component> set = visitComponents.getComponents().stream()
                        .filter(component -> component.isSelected())
                        .collect(Collectors.toSet());


                visitExists.setComponents(set);
            }


            visitService.update(visitExists);
        } else {
            modelAndView.addObject("visit", visit);
            modelAndView.addObject("errorMessageRepair","");
            modelAndView.addObject("errorMessagePick", "");
            modelAndView.setViewName("/clients/products/visits/repair");
            return modelAndView;
        }
        modelAndView.addObject("successMessage", "Wizyta została edytowana");
        modelAndView.setViewName("/home");

        return modelAndView;
    }

    private void setVisitData(Visit visit) {
        Client client = clientService.findById(visit.getClient_id());
        if (client != null) {
            String clientNameSurname = client.getClient_name() + ' ' + client.getClient_surname();
            visit.setClientNameSurname(clientNameSurname);
        }
        User serviceman = userService.findUserById(visit.getEmployerId());
        if(serviceman != null)
            visit.setServisantSurname(serviceman.getLastName());
        Product product = productService.findById(visit.getProduct_id());
        if (product != null)
            visit.setProductName(product.getProduct_name());
    }

}