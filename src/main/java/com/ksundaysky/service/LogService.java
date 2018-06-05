package com.ksundaysky.service;

import com.ksundaysky.model.Log;

import java.util.List;

public interface  LogService {
    public void saveLog(Log log);
    public Log findLogById(int id);
    public List<Log> findAll();
}
