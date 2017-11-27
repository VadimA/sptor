package com.diplom.sptor.repository;

import com.diplom.sptor.domain.Organization;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganizationRepository extends CrudRepository<Organization, Integer> {

    Organization findById(int id);

    Organization save(Organization organization);

    List<Organization> findAll();
}