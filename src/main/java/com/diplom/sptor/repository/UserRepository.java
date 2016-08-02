package com.diplom.sptor.repository;

import com.diplom.sptor.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 31.07.2016.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findById(int id);
    User findBySsoid(String ssoid);
    User save(User user);
    List<User> findAll();
}
