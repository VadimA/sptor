package com.diplom.sptor.service;

import com.diplom.sptor.domain.StatusOfEquipment;

import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
public interface StatusOfEqService {

    public List<StatusOfEquipment> listStatus();

    public StatusOfEquipment getStatusById(int id);
}
