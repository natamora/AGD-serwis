package com.ksundaysky.repository;

import com.ksundaysky.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("logRepository")
public interface LogRepository extends JpaRepository<Log, Long> {
    Log findById(int id);
}
