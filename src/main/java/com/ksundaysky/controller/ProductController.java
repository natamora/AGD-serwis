package com.ksundaysky.controller;

import com.ksundaysky.model.Product;
import com.ksundaysky.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/products")
    public ModelAndView index()
    {
        ModelAndView modelAndView = new ModelAndView();
        List<Product> products = productService.findAll();
        modelAndView.addObject("products",products);
        modelAndView.setViewName("/products/index");
        return modelAndView;
    }

    @RequestMapping(value = "/products/edit/{id}")
    public ModelAndView edit(@PathVariable int id){
        Product product = productService.findById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/products/edit");

        return modelAndView;
    }

    @RequestMapping(value="/products/edit", method=RequestMethod.POST)
    public ModelAndView update(@Valid Product product, BindingResult bindingResult){

        Product productExists = productService.findById(product.getId());
        if (productExists != null) {
            productExists.setName(product.getName());
            productExists.setBrand(product.getBrand());
            productExists.setLastName(product.getLastName());
            productExists.setModel(product.getModel());
            productExists.setProduct_name(product.getProduct_name());
            productExists.setNote(product.getNote());

        }

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("product", product);
            modelAndView.setViewName("/products/edit");

            return modelAndView;
        }

        productService.updateProduct(productExists);
        return new ModelAndView("redirect:/products","udane","edycja");
    }

    @RequestMapping(value="/products/{id}")
    public ModelAndView show(@PathVariable int id){
        Product product = productService.findById(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/products/show");

        return modelAndView;
    }



}
