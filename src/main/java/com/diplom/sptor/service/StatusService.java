package com.diplom.sptor.service;

import com.diplom.sptor.domain.Status;
import com.diplom.sptor.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 31.03.2016.
 */
@Service
@Transactional
public class StatusService {

    @Autowired
    StatusRepository statusRepository;

    public Status addStatus(Status status){
        return  statusRepository.save(status);
    }

    public List<Status> getAllStatus(){
        return statusRepository.findAll();
    }

    public Status getStatusById(int id){
        return statusRepository.findOne(id);
    }

    public void deleteStatus(int id){
        statusRepository.delete(id);
    }

    public Status updateStatus(Status status){
        return statusRepository.save(status);
    }
}
