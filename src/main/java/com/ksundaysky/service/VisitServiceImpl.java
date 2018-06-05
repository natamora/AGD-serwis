package com.ksundaysky.service;

import com.ksundaysky.model.Log;
import com.ksundaysky.model.User;
import com.ksundaysky.model.Visit;
//import com.ksundaysky.repository.RoleRepository;
import com.ksundaysky.repository.LogRepository;
import com.ksundaysky.repository.UserRepository;
import com.ksundaysky.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service("visitService")
public class VisitServiceImpl implements VisitService{

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LogRepository logRepository;

    @Override
    @javax.transaction.Transactional
    public void saveVisit(Visit visit) {
        visitRepository.save(visit);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user= userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("insert");
        log.setTimestamp(time);
        log.setTable_name("Visit");
        log.setAuthor_email(user.getEmail());
        log.setMessage("Dodanie wizyty id: "+ visit.getId());
        logRepository.save(log);
    }

    @Override
    public Visit findVisitById(int id) {
        return visitRepository.findById(id);
    }

    @Override
    public List<Visit> findAll() {
        return visitRepository.findAll();
    }


    public List<Visit> findAllProductVisits(int id){
        return visitRepository.findAllByProductId(id);
    }


// ten update jest do updatowania przez serwisanta, przy edycji wizyty przez rejestrujacego dodac nowy update, zeby w logach sie roznily miedzy soba
    @Override
    public void update(Visit visit) {

//        Role[] role = new Role[1];
////        user.getRoles().toArray(role);
////        Role userRole = roleRepository.findByRole(role[0].getRole());
////        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        visitRepository.save(visit);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user= userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("update");
        log.setTimestamp(time);
        log.setTable_name("Visit");
        log.setAuthor_email(user.getEmail());
        log.setMessage("Edycja - tryb naprawy wizyty id: "+ visit.getId());
        logRepository.save(log);

    }
}
