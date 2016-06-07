package com.diplom.sptor.service;

import com.diplom.sptor.domain.Status;

import java.util.List;

/**
 * Created by user on 31.03.2016.
 */
public interface StatusService {

    public void addStatus(Status spares);

    public List<Status> listStatus();

    public Status getStatusById(int id);

    public void deleteStatus(int id);

    public void updateStatus(Status spares);
}
