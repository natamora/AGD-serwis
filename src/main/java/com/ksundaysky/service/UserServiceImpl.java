package com.ksundaysky.service;

import com.ksundaysky.model.Log;
import com.ksundaysky.model.User;
import com.ksundaysky.repository.LogRepository;
import com.ksundaysky.repository.RoleRepository;
import com.ksundaysky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    LogRepository logRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        userRepository.save(user);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userAutor = userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("insert");
        log.setTimestamp(time);
        log.setTable_name("User");
        log.setAuthor_email(userAutor.getEmail());
        log.setMessage("Dodanie uzytkownika id: "+ user.getId());
        logRepository.save(log);
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateUser(User user) {

        userRepository.save(user);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userAutor = userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("update");
        log.setTimestamp(time);
        log.setTable_name("User");
        log.setAuthor_email(userAutor.getEmail());
        log.setMessage("Edycja uzytkownika id: "+ user.getId());
        logRepository.save(log);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteById(int id) {

        userRepository.deleteById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userAutor = userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("delete");
        log.setTimestamp(time);
        log.setTable_name("User");
        log.setAuthor_email(userAutor.getEmail());
        log.setMessage("Usuniecie uzytkownika id: "+ id);
        logRepository.save(log);

    }

}