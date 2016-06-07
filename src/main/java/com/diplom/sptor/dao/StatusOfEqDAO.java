package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Status;
import com.diplom.sptor.domain.StatusOfEquipment;

import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
public interface StatusOfEqDAO {

    public List<StatusOfEquipment> listStatus();

    public StatusOfEquipment getStatusById(int id);
}
