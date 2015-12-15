package com.diplom.sptor.dao;

import com.diplom.sptor.domain.RepairSheet;
import com.diplom.sptor.domain.Spares;

import java.util.List;

/**
 * Created by user on 16.12.2015.
 */
public interface RepairSheetDAO {

    public void addRepairSheet(RepairSheet repairSheet);

    public List<RepairSheet> listRepairSheets();

    public RepairSheet getRepairSheetById(int id);

    public void deleteRepairSheet(int id);

    public void updateRepairSheet(RepairSheet repairSheet);
}
