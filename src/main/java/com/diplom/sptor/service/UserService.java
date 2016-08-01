package com.diplom.sptor.service;

import com.diplom.sptor.domain.User;
import com.diplom.sptor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by user on 31.01.2016.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(int id){
        return userRepository.findById(id);
    };

    public User getUserBySso(String sso){
        return userRepository.findBySSO(sso);
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
