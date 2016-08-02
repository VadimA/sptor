package com.diplom.sptor.service;

import com.diplom.sptor.domain.Parameters;
import com.diplom.sptor.domain.TypeOfEquipment;
import com.diplom.sptor.repository.ParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 02.03.2016.
 */
@Service
@Transactional
public class ParametersService {

    @Autowired
    ParametersRepository parametersRepository;

    public Parameters addParameter(Parameters parameter){
        return parametersRepository.save(parameter);
    }

    public List<Parameters> getAllParameters(){
        return parametersRepository.findAll();
    }

    public Parameters getParameterById(int id){
        return parametersRepository.findOne(id);
    }

    public void deleteParameter(int id){
        parametersRepository.delete(id);
    }

    public Parameters updateParameter(Parameters parameter){
        return parametersRepository.save(parameter);
    }

    public List<Parameters> getParametersByType(TypeOfEquipment type){
        return parametersRepository.findByTypeOfEquipment(type);
    }
}
