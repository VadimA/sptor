package com.diplom.sptor.service;

import com.diplom.sptor.domain.StatusOfEquipment;
import com.diplom.sptor.repository.StatusOfEqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
@Service
@Transactional
public class StatusOfEqService {

    @Autowired
    StatusOfEqRepository statusOfEqRepository;

    public List<StatusOfEquipment> listStatus(){
        return statusOfEqRepository.findAll();
    }

    public StatusOfEquipment getStatusById(int id){
        return statusOfEqRepository.findOne(id);
    }
}
