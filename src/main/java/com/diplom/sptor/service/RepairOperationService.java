package com.diplom.sptor.service;

import com.diplom.sptor.domain.RepairOperation;
import com.diplom.sptor.repository.RepairOperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RepairOperationService {

    @Autowired
    RepairOperationRepository repairOperationRepository;

    public void addRepairOperation(RepairOperation repairOperation){
        repairOperationRepository.save(repairOperation);
    }

    public List<RepairOperation> getAllRepairSheets(){
        return repairOperationRepository.findAll();
    }

    public void deleteRepairSheet(int id){
        repairOperationRepository.delete(id);
    }

    public List<RepairOperation> getRepairSheetByGraphicId(int graphicId){
        return repairOperationRepository.findByGraphicId(graphicId);
    }

    public RepairOperation update(RepairOperation repairOperation){
        return repairOperationRepository.save(repairOperation);
    }
}
