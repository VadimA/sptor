package com.diplom.sptor.service;

import com.diplom.sptor.domain.User;

import java.util.List;

/**
 * Created by user on 31.01.2016.
 */
public interface UserService {

    User findById(int id);

    User findBySso(String sso);

    void addUser(User user);

    List<User> getUsers();
}
