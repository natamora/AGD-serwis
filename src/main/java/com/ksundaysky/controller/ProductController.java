package com.ksundaysky.controller;

import com.ksundaysky.model.Product;
import com.ksundaysky.model.Role;
import com.ksundaysky.model.User;
import com.ksundaysky.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
@Controller
public class ProductController {


    @Autowired
    ProductService productService;

    @RequestMapping(value = {"/products/create"}, method = RequestMethod.GET)
    public ModelAndView createNewUser() {
        ModelAndView modelAndView = new ModelAndView();
        Product product = new Product();
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/products/create");
        return modelAndView;
    }

    @RequestMapping(value = "/products/create", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid Product product, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

            productService.saveProduct(product);
            modelAndView.addObject("successMessage", "Produkt został dodany");
            modelAndView.addObject("product", new Product());
            modelAndView.setViewName("/home");


        return modelAndView;
    }


}
