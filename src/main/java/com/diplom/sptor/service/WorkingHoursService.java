package com.diplom.sptor.service;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.WorkingHours;
import com.diplom.sptor.repository.WorkingHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 14.03.2016.
 */
@Service
@Transactional
public class WorkingHoursService {

    @Autowired
    WorkingHoursRepository workingHoursRepository;

    public WorkingHours addWorkingHours(WorkingHours workingHours){
        return workingHoursRepository.save(workingHours);
    }

    public List<WorkingHours> getAllWorkingHours(){
        return workingHoursRepository.findAll();
    }

    public List<WorkingHours> getWorkingHoursByEquipment(Equipment equipment){
        return workingHoursRepository.findByEquipment(equipment);
    }

    public void deleteWorkingHours(int id){
         workingHoursRepository.delete(id);
    }

    public WorkingHours updateWorkingHours(WorkingHours workingHours){
        return workingHoursRepository.save(workingHours);
    }
}
