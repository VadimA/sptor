package com.diplom.sptor.service;

import com.diplom.sptor.dao.DownTimeDAO;
import com.diplom.sptor.domain.DownTime;
import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.WorkingHours;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 14.04.2016.
 */
@Component
public class DownTimeServiceImpl implements DownTimeService{

    private DownTimeDAO downTimeDAO;

    public void setDownTimeDAO(DownTimeDAO downTimeDAO) {
        this.downTimeDAO = downTimeDAO;
    }

    @Transactional
    public void addDownTime(DownTime downTime) {
        this.downTimeDAO.addDownTime(downTime);
    }

    @Transactional
    public List<DownTime> listDownTime() {
        return this.downTimeDAO.listDownTime();
    }

    @Transactional
    public List<DownTime> getDownTimeByEquipmentId(Equipment equipment) {
        return this.downTimeDAO.getDownTimeByEquipmentId(equipment);
    }

    @Transactional
    public void deleteDownTime(int id) {
        this.downTimeDAO.deleteDownTime(id);
    }

    @Transactional
    public void updateDownTime(DownTime downTime) {
        this.downTimeDAO.updateDownTime(downTime);
    }

}
