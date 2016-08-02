package com.diplom.sptor.service;

import com.diplom.sptor.domain.Spares;
import com.diplom.sptor.repository.SpareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 31.07.2016.
 */
@Service
@Transactional
public class SpareService {

    @Autowired
    private SpareRepository spareRepository;

    public List<Spares> getAllSpares(){
        return this.spareRepository.findAll();
    }

    public Spares saveSpare(Spares spare){
        return spareRepository.save(spare);
    }

    public Spares getSpareById(int id){
        return spareRepository.findOne(id);
    }

}
