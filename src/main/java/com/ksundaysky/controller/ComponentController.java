package com.ksundaysky.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ksundaysky.model.Component;
import com.ksundaysky.model.Role;
import com.ksundaysky.model.Visit;
import com.ksundaysky.service.ComponentService;
import com.ksundaysky.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
public class ComponentController {


    @Autowired
    ComponentService componentService;
    @Autowired
    private VisitService visitService;

    @PreAuthorize("hasAnyAuthority('ADMIN','SERWISANT')")
    @RequestMapping(value = {"/components/create"}, method = RequestMethod.GET)
    public ModelAndView createNewComponent() {
        ModelAndView modelAndView = new ModelAndView();
        Component component = new Component();
        modelAndView.addObject("component", component);
        modelAndView.setViewName("/components/create");
        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SERWISANT')")
    @RequestMapping(value = "/components/create", method = RequestMethod.POST)
    public ModelAndView createNewComponent(@Valid Component component, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/components/create");
        }
        else {
            componentService.saveComponent(component);
            modelAndView.addObject("successMessage", "Komponent został dodany");
            modelAndView.addObject("component", new Component());
            modelAndView.setViewName("/home");
        }


        return modelAndView;
    }


    @RequestMapping(value = "/components")
    public ModelAndView index(HttpServletRequest request)
    {
        ModelAndView modelAndView = new ModelAndView();
        List<Component> components = componentService.findAll();
        modelAndView.addObject("components",components);
        modelAndView.setViewName("/components/index");
        String successMessage = (String)request.getSession().getAttribute("successMessage");
        if( successMessage != null) {
            modelAndView.addObject("successMessage", successMessage);
            request.getSession().removeAttribute("successMessage");
        }

        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SERWISANT')")
    @RequestMapping(value = "/components/edit/{id}")
    public ModelAndView edit(@PathVariable int id){
        Component component = componentService.findById(id);

        ModelAndView modelAndView = new ModelAndView();
        if(component == null){
            modelAndView.addObject("errorMessage", "Komponent o danym id nie istnieje");
        }
        modelAndView.addObject("component", component);
        modelAndView.setViewName("/components/edit");

        return modelAndView;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SERWISANT')")
    @RequestMapping(value = "/components/delete/{id}")
    public ModelAndView delete(@PathVariable int id){
        Component component = componentService.findById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("component", component);
        modelAndView.setViewName("/components/delete");

        return modelAndView;
    }


    @PreAuthorize("hasAnyAuthority('ADMIN','SERWISANT')")
    @RequestMapping(value = {"/components/delete/accept/{id}"}, method = RequestMethod.GET)
    public ModelAndView deleteAccept( @PathVariable int id) {

        ModelAndView modelAndView = new ModelAndView();
        Component component = componentService.findById(id);

        for(Visit visit : visitService.findAll()){
            Set<Component> componentSet = visit.getComponents();
            if(componentSet.contains(component)) {
                componentSet.remove(component);
                visitService.saveVisit(visit);
            }
        }

        componentService.deleteById(id);
        modelAndView.setViewName("/home");
        modelAndView.addObject("successMessage", "Komponent został usunięty");
        return modelAndView;

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','SERWISANT')")
    @RequestMapping(value="/components/edit", method=RequestMethod.POST)
    public ModelAndView update(@Valid Component component, BindingResult bindingResult, HttpServletRequest request){

        Component componentExists = componentService.findById(component.getId());
        if (componentExists != null) {
            componentExists.setComponent_name(component.getComponent_name());
            componentExists.setType(component.getType());
            componentExists.setPrice(component.getPrice());
        }

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("component", component);
            modelAndView.setViewName("/components/edit");

            return modelAndView;
        }

        request.getSession().setAttribute("successMessage", "Komponent został poprawnie zmodyfikowany!!");
        componentService.updateComponent(componentExists);


        return new ModelAndView("redirect:/components");
    }

    @RequestMapping(value="/components/{id}")
    public ModelAndView show(@PathVariable int id){
        Component component = componentService.findById(id);

        ModelAndView modelAndView = new ModelAndView();
        if(component == null){
            modelAndView.addObject("errorMessage","Komponent o danym id nie istnieje");
        }
        modelAndView.addObject("component", component);
        modelAndView.setViewName("/components/show");

        return modelAndView;
    }

    @RequestMapping(path="/components/getall", method=RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Component> getAllEmployees(){
        ObjectMapper objectMapper = new ObjectMapper();
        String str = null;
        try {
            str = objectMapper.writeValueAsString(componentService.findAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return componentService.findAll();
    }
}
