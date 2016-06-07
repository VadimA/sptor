package com.diplom.sptor.dao;

import com.diplom.sptor.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 16.12.2015.
 */
@Repository
public class RepairSheetDAOImpl implements RepairSheetDAO {

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void addRepairSheet(RepairSheet repairSheet) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(repairSheet);
    }

    public List<RepairSheet> listRepairSheets() {
        Session session = this.sessionFactory.getCurrentSession();
        List<RepairSheet> repairSheets = session.createQuery(" select repair from com.diplom.sptor.domain.RepairSheet repair order by status").list();
        return  repairSheets;
    }

    public RepairSheet getRepairSheetById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        RepairSheet repairSheet = (RepairSheet) session.load(RepairSheet.class, new Integer(id));
        return repairSheet;
    }

    public void deleteRepairSheet(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        RepairSheet repairSheet = (RepairSheet) session.load(RepairSheet.class, new Integer(id));
        if(null != repairSheet){
            session.delete(repairSheet);
        }
    }

    public void updateRepairSheet(RepairSheet repairSheet) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(repairSheet);
    }

    public List<RepairSheet> getRepairSheetByStatus(Status status){
        Session session = this.sessionFactory.getCurrentSession();
        List<RepairSheet> repairList = session.createQuery(" select repair from com.diplom.sptor.domain.RepairSheet repair " +
                "where repair.status =:id ").setParameter("id", status).list();
        return  repairList;
    }

    public List<RepairSheet> getRepairSheetByResponsibleForDelivery(User user){
        Session session = this.sessionFactory.getCurrentSession();
        List<RepairSheet> repairList = session.createQuery(" select repair from com.diplom.sptor.domain.RepairSheet repair " +
                "where repair.responsible_for_delivery =:user ").setParameter("user", user).list();
        return  repairList;
    }

    public List<RepairSheet> getRepairSheetByResponsibleAndStatus(User user, int status){
        Session session = this.sessionFactory.getCurrentSession();
        List<RepairSheet> repairList = session.createQuery(" select repair from com.diplom.sptor.domain.RepairSheet repair " +
                "where repair.responsible_for_delivery =:user and repair.status.status_id =:status ").setParameter("user", user).setParameter("status", status).list();
        return  repairList;
    }

    public List<RepairSheet> getTypeOfMaintenanceOfRepairByEquipmentAndComponents(Equipment equipment,Components components){
        Session session = this.sessionFactory.getCurrentSession();
        List<RepairSheet> repairList = session.createQuery(" select repair from com.diplom.sptor.domain.RepairSheet repair " +
                "where equipment =:equipment and component =:component ").setParameter("equipment", equipment).setParameter("component", components).list();
        return  repairList;
    }

    public List<RepairSheet> getRepairSheetByEquipment(Equipment equipment){
        Session session = this.sessionFactory.getCurrentSession();
        List<RepairSheet> repairList = session.createQuery(" select repair from com.diplom.sptor.domain.RepairSheet repair " +
                "where equipment =:equipment order by start_date desc ").setParameter("equipment", equipment).list();
        return  repairList;
    }
}
