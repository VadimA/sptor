package com.diplom.sptor.service;

import com.diplom.sptor.dao.StatusDAO;
import com.diplom.sptor.domain.Status;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 31.03.2016.
 */
@Service
public class StatusServiceImpl implements StatusService{

    private StatusDAO statusDAO;

    public void setStatusDAO(StatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }

    @Transactional
    public void addStatus(Status status){
        this.statusDAO.addStatus(status);
    }

    @Transactional
    public List<Status> listStatus(){
        return this.statusDAO.listStatus();
    }

    @Transactional
    public Status getStatusById(int id){
        return this.statusDAO.getStatusById(id);
    }

    @Transactional
    public void deleteStatus(int id){
        this.statusDAO.deleteStatus(id);
    }

    @Transactional
    public void updateStatus(Status status){
        this.statusDAO.updateStatus(status);
    }
}
