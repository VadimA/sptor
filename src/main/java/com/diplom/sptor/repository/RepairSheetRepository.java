package com.diplom.sptor.repository;

import com.diplom.sptor.domain.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 02.08.2016.
 */
public interface RepairSheetRepository extends CrudRepository<RepairSheet, Integer> {

    List<RepairSheet> findAll();
    List<RepairSheet> findByResponsibleForDelivery(User responsibleForDelivery);
    List<RepairSheet> findByResponsibleForDeliveryAndStatus(User responsibleForDelivery, Status status);
    List<RepairSheet> findByEquipmentAndComponent(Equipment equipment, Components component);
    List<RepairSheet> findByStatus(Status status);
    List<RepairSheet> findByEquipment(Equipment equipment);
}
