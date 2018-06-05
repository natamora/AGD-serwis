package com.ksundaysky.service;

import com.ksundaysky.model.Component;
import com.ksundaysky.model.Log;
import com.ksundaysky.model.User;
import com.ksundaysky.repository.ComponentRepository;
import com.ksundaysky.repository.LogRepository;
import com.ksundaysky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service("componentService")
public class ComponentServiceImpl implements ComponentService {

    @Autowired
    ComponentRepository componentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LogRepository logRepository;

    @Override
    public void saveComponent(Component component) {

        componentRepository.save(component);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("insert");
        log.setTimestamp(time);
        log.setTable_name("Component");
        log.setAuthor_email(user.getEmail());
        log.setMessage("Dodanie komponentu id: "+ component.getId());
        logRepository.save(log);
    }

    @Override
    public List<Component> findAll() {
        return componentRepository.findAll();
    }

    @Override
    public Component findById(int component_id) { return componentRepository.findById(component_id);}

    @Override
    public void updateComponent(Component component) {
        componentRepository.save(component);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("update");
        log.setTimestamp(time);
        log.setTable_name("Component");
        log.setAuthor_email(user.getEmail());
        log.setMessage("Edycja komponentu id: "+ component.getId());
        logRepository.save(log);
    }

    @Override
    public void deleteById(int component_id){

        componentRepository.deleteById(component_id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("delete");
        log.setTimestamp(time);
        log.setTable_name("Component");
        log.setAuthor_email(user.getEmail());
        log.setMessage("Usuniecie komponentu id: "+ component_id);
        logRepository.save(log);
    }
}
