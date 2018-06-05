package com.ksundaysky.service;

import com.ksundaysky.model.Log;
import com.ksundaysky.model.Product;
import com.ksundaysky.model.User;
import com.ksundaysky.repository.LogRepository;
import com.ksundaysky.repository.ProductRepository;
import com.ksundaysky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LogRepository logRepository;
    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("insert");
        log.setTimestamp(time);
        log.setTable_name("Product");
        log.setAuthor_email(user.getEmail());
        log.setMessage("Dodanie produktu id: "+ product.getId());
        logRepository.save(log);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(int id) { return productRepository.findById(id);}

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("update");
        log.setTimestamp(time);
        log.setTable_name("Product");
        log.setAuthor_email(user.getEmail());
        log.setMessage("Edycja produktu id: "+ product.getId());
        logRepository.save(log);
    }

    @Override
    public void deleteById(int id){

        productRepository.deleteById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("delete");
        log.setTimestamp(time);
        log.setTable_name("Product");
        log.setAuthor_email(user.getEmail());
        log.setMessage("Usuniecie produktu id: "+ id);
        logRepository.save(log);
    }

    @Override
    public Product findBySerialNumber(String serial_number) {
        return productRepository.findBySerial(serial_number);
    }
}
