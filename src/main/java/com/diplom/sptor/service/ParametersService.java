package com.diplom.sptor.service;

import com.diplom.sptor.domain.Parameters;
import com.diplom.sptor.domain.TypeOfEquipment;

import java.util.List;

/**
 * Created by user on 02.03.2016.
 */
public interface ParametersService {

    public void addParameter(Parameters parameters);

    public List<Parameters> listParameters();

    public Parameters getParameterById(int id);

    public void deleteParameter(int id);

    public void updateParameter(Parameters parameters);

    public List<Parameters> getParametersByType(TypeOfEquipment type);
}
