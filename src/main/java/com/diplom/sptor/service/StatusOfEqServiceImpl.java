package com.diplom.sptor.service;

import com.diplom.sptor.dao.StatusOfEqDAO;
import com.diplom.sptor.domain.StatusOfEquipment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
@Service
public class StatusOfEqServiceImpl implements StatusOfEqService{

    private StatusOfEqDAO statusOfEqDAO;

    public void setStatusOfEqDAO(StatusOfEqDAO statusOfEqDAO) {
        this.statusOfEqDAO = statusOfEqDAO;
    }

    @Transactional
    public List<StatusOfEquipment> listStatus(){
        return this.statusOfEqDAO.listStatus();
    }

    @Transactional
    public StatusOfEquipment getStatusById(int id){
        return this.statusOfEqDAO.getStatusById(id);
    }
}
