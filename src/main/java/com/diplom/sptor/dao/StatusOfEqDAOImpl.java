package com.diplom.sptor.dao;

import com.diplom.sptor.domain.StatusOfEquipment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 24.05.2016.
 */
@Repository
@Transactional
public class StatusOfEqDAOImpl implements StatusOfEqDAO{

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<StatusOfEquipment> listStatus() {
        Session session = this.sessionFactory.getCurrentSession();
        List<StatusOfEquipment> statusList = session.createQuery("from com.diplom.sptor.domain.StatusOfEquipment ").list();
        return  statusList;
    }

    public StatusOfEquipment getStatusById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        StatusOfEquipment status = (StatusOfEquipment) session.load(StatusOfEquipment.class, new Integer(id));
        return status;
    }
}
