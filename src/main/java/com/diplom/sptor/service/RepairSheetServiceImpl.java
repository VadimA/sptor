package com.diplom.sptor.service;

import com.diplom.sptor.dao.RepairSheetDAO;
import com.diplom.sptor.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 16.12.2015.
 */
@Service
public class RepairSheetServiceImpl implements RepairSheetService {

    private RepairSheetDAO repairSheetDAO;

    public void setRepairSheetDAO(RepairSheetDAO repairSheetDAO) {
        this.repairSheetDAO = repairSheetDAO;
    }

    @Transactional
    public void addRepairSheet(RepairSheet repairSheet) {
        this.repairSheetDAO.addRepairSheet(repairSheet);
    }

    @Transactional
    public List<RepairSheet> listRepairSheets() {
        return this.repairSheetDAO.listRepairSheets();
    }

    @Transactional
    public RepairSheet getRepairSheetById(int id) {
        return this.repairSheetDAO.getRepairSheetById(id);
    }

    @Transactional
    public void deleteRepairSheet(int id) {
        this.repairSheetDAO.deleteRepairSheet(id);
    }

    @Transactional
    public void updateRepairSheet(RepairSheet repairSheet) {
        this.repairSheetDAO.updateRepairSheet(repairSheet);
    }

    @Transactional
    public List<RepairSheet> getRepairSheetByStatus(Status status){
        return this.repairSheetDAO.getRepairSheetByStatus(status);
    }
    @Transactional
    public List<RepairSheet> getRepairSheetByResponsibleForDelivery(User user){
        return this.repairSheetDAO.getRepairSheetByResponsibleForDelivery(user);
    }
    @Transactional
    public List<RepairSheet> getRepairSheetByResponsibleAndStatus(User user, int status){
        return this.repairSheetDAO.getRepairSheetByResponsibleAndStatus(user, status);
    }

    @Transactional
    public List<RepairSheet> getTypeOfMaintenanceOfRepairByEquipmentAndComponents(Equipment equipment, Components components){
        return this.repairSheetDAO.getTypeOfMaintenanceOfRepairByEquipmentAndComponents(equipment,components);
    }

    @Transactional
    public List<RepairSheet> getRepairSheetByEquipment(Equipment equipment){
        return this.repairSheetDAO.getRepairSheetByEquipment(equipment);
    }
}
