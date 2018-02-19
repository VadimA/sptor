package com.diplom.sptor.repository;

import com.diplom.sptor.domain.RepairOperation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepairOperationRepository extends CrudRepository<RepairOperation, Integer> {

    List<RepairOperation> findAll();
    List<RepairOperation> findByGraphicId(int graphicId);
}
