package com.diplom.sptor.dao;

import com.diplom.sptor.domain.User;

import java.util.List;

/**
 * Created by user on 31.01.2016.
 */
public interface UserDao {

    User findById(int id);

    User findBySSO(String sso);

    void addUser(User user);

    List<User> getUsers();
}