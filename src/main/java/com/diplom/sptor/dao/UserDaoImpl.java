package com.diplom.sptor.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.diplom.sptor.domain.User;

import java.util.List;

@Repository
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public User findById(int id) {
        return getByKey(id);
    }

    public User findBySSO(String sso) {
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("ssoid", sso));
        return (User) crit.uniqueResult();
    }

    public void addUser(User user){
        User newUser = user;
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(user);
    }

    public List<User> getUsers(){
        Session session = this.sessionFactory.getCurrentSession();
        List<User> userList = session.createQuery("from com.diplom.sptor.domain.User ").list();
        return userList;
    }


}