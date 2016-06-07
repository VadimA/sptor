package com.diplom.sptor.service;

import com.diplom.sptor.dao.ComponentsDAO;
import com.diplom.sptor.dao.ParametersDAO;
import com.diplom.sptor.domain.Components;
import com.diplom.sptor.domain.Parameters;
import com.diplom.sptor.domain.TypeOfEquipment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 02.03.2016.
 */
@Component
public class ParametersServiceImpl implements ParametersService {

    private ParametersDAO parametersDao;

    public void setParametersDao(ParametersDAO parametersDao) {
        this.parametersDao = parametersDao;
    }

    @Transactional
    public void addParameter(Parameters parameters) {
        this.parametersDao.addParameter(parameters);
    }

    @Transactional
    public List<Parameters> listParameters() {
        return this.parametersDao.listParameters();
    }

    @Transactional
    public Parameters getParameterById(int id) {
        return this.parametersDao.getParameterById(id);
    }

    @Transactional
    public void deleteParameter(int id) {
        this.parametersDao.deleteParameter(id);
    }

    @Transactional
    public void updateParameter(Parameters parameters) {
        this.parametersDao.updateParameter(parameters);
    }

    @Transactional
    public List<Parameters> getParametersByType(TypeOfEquipment type){
        return this.parametersDao.getParametersByType(type);
    }
}
