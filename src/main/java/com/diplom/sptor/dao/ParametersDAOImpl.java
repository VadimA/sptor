package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Components;
import com.diplom.sptor.domain.Parameters;
import com.diplom.sptor.domain.TypeOfEquipment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 02.03.2016.
 */
@Repository
@Transactional
public class ParametersDAOImpl implements ParametersDAO{


    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void addParameter(Parameters parameters) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(parameters);
    }

    public List<Parameters> listParameters() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Parameters> paramList = session.createQuery("from com.diplom.sptor.domain.Parameters ").list();
        return  paramList;
    }

    public Parameters getParameterById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Parameters parameters  = (Parameters) session.load(Parameters.class, new Integer(id));
        return parameters;
    }

    public void deleteParameter(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Parameters parameters = (Parameters) session.load(Parameters.class, new Integer(id));
        if(null != parameters){
            session.delete(parameters);
        }
    }

    public void updateParameter(Parameters parameters) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(parameters);
    }

    public List<Parameters> getParametersByType(TypeOfEquipment type){
        Session session = this.sessionFactory.getCurrentSession();
        List<Parameters> paramsList = session.createQuery(" select param from com.diplom.sptor.domain.Parameters param " +
                "where param.type_of_equipment_parameters =:id ").setParameter("id", type).list();
        return  paramsList;
    }
}
