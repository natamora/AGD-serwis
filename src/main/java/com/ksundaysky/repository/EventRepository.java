package com.ksundaysky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

import com.ksundaysky.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAll();
    Event save(Event event);
    void delete(Event event);

    @Query("select b from Event b " +
            "where b.start between ?1 and ?2 and b.end between ?1 and ?2")
    List<Event> findByDatesBetween(Date start, Date end);
}