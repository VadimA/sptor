package com.diplom.sptor.dao;

        import com.diplom.sptor.domain.*;

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

    public List<RepairSheet> getRepairSheetByStatus(Status status);

    public List<RepairSheet> getRepairSheetByResponsibleForDelivery(User user);

    public List<RepairSheet> getRepairSheetByResponsibleAndStatus(User user, int status);

    public List<RepairSheet> getTypeOfMaintenanceOfRepairByEquipmentAndComponents(Equipment equipment, Components components);

    public List<RepairSheet> getRepairSheetByEquipment(Equipment equipment);
}
