package com.diplom.sptor.service;

import com.diplom.sptor.domain.DownTime;
import com.diplom.sptor.domain.Equipment;

import java.util.List;

/**
 * Created by user on 14.04.2016.
 */
public interface DownTimeService {

    public void addDownTime(DownTime downTime);

    public List<DownTime> listDownTime();

    public List<DownTime> getDownTimeByEquipmentId(Equipment equipment);

    public void deleteDownTime(int id);

    public void updateDownTime(DownTime downTime);
}
