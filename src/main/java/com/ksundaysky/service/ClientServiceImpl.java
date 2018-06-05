package com.ksundaysky.service;

import com.ksundaysky.model.Client;
import com.ksundaysky.model.Log;
import com.ksundaysky.model.User;
import com.ksundaysky.repository.ClientRepository;
import com.ksundaysky.repository.LogRepository;
import com.ksundaysky.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.sql.Timestamp;

@Service("clientService")
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LogRepository logRepository;
    @Override
    public void saveClient(Client client) {

        clientRepository.save(client);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("insert");
        log.setTimestamp(time);
        log.setTable_name("Client");
        log.setAuthor_email(user.getEmail());
        log.setMessage("Dodanie klienta id: "+ client.getId());

        logRepository.save(log);


    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(int id) { return clientRepository.findById(id);}

    @Override
    public void updateClient(Client client) {

        clientRepository.save(client);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("update");
        log.setTimestamp(time);
        log.setTable_name("Client");
        log.setAuthor_email(user.getEmail());
        log.setMessage("Edycja klienta id: "+ client.getId());
        logRepository.save(log);

    }

    @Override
    public void deleteById(int id) {

        clientRepository.deleteById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Log log = new Log();
        log.setAction_type("delete");
        log.setTimestamp(time);
        log.setTable_name("Client");
        log.setAuthor_email(user.getEmail());
        log.setMessage("Usuniecie klienta id: "+ id);
        //userRepository.delete(Long.valueOf((long)id));
        logRepository.save(log);
    }
}
