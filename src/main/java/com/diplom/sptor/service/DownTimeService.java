package com.diplom.sptor.service;

import com.diplom.sptor.domain.DownTime;
import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.repository.DownTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 14.04.2016.
 */
@Service
@Transactional
public class DownTimeService {

    @Autowired
    DownTimeRepository downTimeRepository;

    public DownTime addDownTime(DownTime downTime){
        return downTimeRepository.save(downTime);
    }

    public List<DownTime> egetAllDownTimes(){
        return downTimeRepository.findAll();
    }

    public List<DownTime> getDownTimeByEquipment(Equipment equipment){
        return downTimeRepository.findByEquipment(equipment);
    }

    public void deleteDownTime(int id){
        downTimeRepository.delete(id);
    }

    public DownTime updateDownTime(DownTime downTime){
        return downTimeRepository.save(downTime);
    }
}
