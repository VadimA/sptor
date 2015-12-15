package com.diplom.sptor.service;

import com.diplom.sptor.domain.RepairSheet;

import java.util.List;

/**
 * Created by user on 16.12.2015.
 */
public interface RepairSheetService {

    public void addRepairSheet(RepairSheet repairSheet);

    public List<RepairSheet> listRepairSheets();

    public RepairSheet getRepairSheetById(int id);

    public void deleteRepairSheet(int id);

    public void updateRepairSheet(RepairSheet repairSheet);
}
