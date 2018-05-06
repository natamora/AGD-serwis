package com.ksundaysky.repository;

import com.ksundaysky.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("clientRepository")
public interface ClientRepository extends JpaRepository<Client,Integer>{
    Client findById(int id);

    @Transactional
    Long deleteById(int id);
}
