package com.diplom.sptor.service;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Subdivisions;
import com.diplom.sptor.repository.SubdivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 08.12.2015.
 */
@Service
@Transactional
public class SubdivisionService {

    @Autowired
    SubdivisionRepository subdivisionRepository;

    public Subdivisions addSubdivision(Subdivisions subdivision){
        return subdivisionRepository.save(subdivision);
    }

    public List<Subdivisions> getAllSubdivisions(){
        return subdivisionRepository.findAll();
    }

    public Subdivisions getSubdivisionById(int id){
        return subdivisionRepository.findOne(id);
    }

    public void deleteSubdivision(int id){
        subdivisionRepository.delete(id);
    }

    public Subdivisions updateSubdivision(Subdivisions subdivision){
        return subdivisionRepository.save(subdivision);
    }

}
