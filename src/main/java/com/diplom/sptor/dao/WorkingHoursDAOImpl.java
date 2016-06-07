package com.diplom.sptor.dao;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Parameters;
import com.diplom.sptor.domain.WorkingHours;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by user on 14.03.2016.
 */
public class WorkingHoursDAOImpl implements WorkingHoursDAO{


    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void addWorkingHours(WorkingHours parameters) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(parameters);
    }

    public List<WorkingHours> listWorkingHours() {
        Session session = this.sessionFactory.getCurrentSession();
        List<WorkingHours> workingHourses = session.createQuery("from com.diplom.sptor.domain.WorkingHours ").list();
        return  workingHourses;
    }

    public List<WorkingHours> getWorkingHoursByEquipmentId(Equipment equipment) {
        Session session = this.sessionFactory.getCurrentSession();
        List<WorkingHours> workingHourses = session.createQuery("select work from com.diplom.sptor.domain.WorkingHours work " +
                "where work.equipment =:id order by work.date_of_adding").setParameter("id", equipment).list();
        return  workingHourses;
    }

    public void deleteWorkingHours(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        WorkingHours workingHours = (WorkingHours) session.load(WorkingHours.class, new Integer(id));
        if(null != workingHours){
            session.delete(workingHours);
        }
    }

    public void updateWorkingHours(WorkingHours workingHours) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(workingHours);
    }
}
