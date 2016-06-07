package com.diplom.sptor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diplom.sptor.dao.UserDao;
import com.diplom.sptor.domain.User;

import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService{

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findById(int id) {
        return userDao.findById(id);
    }

    public User findBySso(String sso) {
        return userDao.findBySSO(sso);
    }

    public void addUser(User user){
        userDao.addUser(user);
    }

    public List<User> getUsers(){
        return userDao.getUsers();
    }
}
