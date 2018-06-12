package com.ksundaysky.service;

import com.ksundaysky.model.Workdays;

import java.util.List;

public interface WorkdaysService {
    public Workdays getWorkdaysById(int id);
    public void saveWorkdays(Workdays workdays);
    public void deleteById(int id);
}
