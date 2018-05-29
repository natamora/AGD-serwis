package com.ksundaysky.service;


import com.ksundaysky.model.Workdays;
import com.ksundaysky.repository.WorkdaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("workdaysService")
public class WorkdaysServiceImpl implements WorkdaysService{

    @Autowired
    private WorkdaysRepository workdaysRepository;

    @Override
    public Workdays getWorkdaysById(int id){
        return workdaysRepository.getWorkdaysById(id);
    }
    @Override
    public void saveWorkdays(Workdays user) {
        workdaysRepository.save(user);
    }

    @Override
    public void deleteById(int id) {

        workdaysRepository.deleteById(id);

    }

}
