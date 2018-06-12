package com.ksundaysky.repository;

import com.ksundaysky.model.Workdays;
import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("workdaysRepository")
public interface WorkdaysRepository extends JpaRepository<Workdays, Integer> {

    Workdays getWorkdaysById(int id);
    Workdays findById(int id);
    @Transactional
    Integer deleteById(int id);

}
