package com.diplom.sptor.service;

import com.diplom.sptor.domain.*;
import com.diplom.sptor.repository.RepairSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 16.12.2015.
 */
@Service
@Transactional
public class RepairSheetService {

    @Autowired
    RepairSheetRepository repairSheetRepository;

    public RepairSheet addRepairSheet(RepairSheet repairSheet){
        return repairSheetRepository.save(repairSheet);
    }

    public List<RepairSheet> getAllRepairSheets(){
        return repairSheetRepository.findAll();
    }

    public RepairSheet getRepairSheetById(int id){
        return repairSheetRepository.findOne(id);
    }

    public void deleteRepairSheet(int id){
        repairSheetRepository.delete(id);
    }

    public RepairSheet updateRepairSheet(RepairSheet repairSheet){
        return repairSheetRepository.save(repairSheet);
    }

    public List<RepairSheet> getRepairSheetByStatus(Status status){
        return repairSheetRepository.findByStatus(status);
    }

    public List<RepairSheet> getRepairSheetByResponsibleForDelivery(User user){
        return repairSheetRepository.findByResponsibleForDelivery(user);
    }

    public List<RepairSheet> getRepairSheetByResponsibleAndStatus(User user, Status status){
        return repairSheetRepository.findByResponsibleForDeliveryAndStatus(user,status);
    }

    public List<RepairSheet> getTypeOfMaintenanceOfRepairByEquipmentAndComponents(Equipment equipment, Components component){
        return repairSheetRepository.findByEquipmentAndComponent(equipment,component);
    }

    public List<RepairSheet> getRepairSheetByEquipment(Equipment equipment){
        return repairSheetRepository.findByEquipment(equipment);
    }
}
