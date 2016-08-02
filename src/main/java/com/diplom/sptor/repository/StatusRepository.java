package com.diplom.sptor.repository;

import com.diplom.sptor.domain.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 02.08.2016.
 */
public interface StatusRepository  extends CrudRepository<Status, Integer> {

    List<Status> findAll();
}
