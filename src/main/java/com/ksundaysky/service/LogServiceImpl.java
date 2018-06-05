package com.ksundaysky.service;

import com.ksundaysky.model.Log;
import com.ksundaysky.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Override
    public void saveLog(Log log) {
        logRepository.save(log);
    }

    @Override
    public Log findLogById(int id) {
        return logRepository.findById(id);
    }

    @Override
    public List<Log> findAll() {
        return logRepository.findAll();
    }

}
