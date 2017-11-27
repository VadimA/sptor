package com.diplom.sptor.service;

import com.diplom.sptor.domain.Organization;
import com.diplom.sptor.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrganizationService {


    @Autowired
    private OrganizationRepository organizationRepository;

    public Organization getOrganizationById(int id){
        return organizationRepository.findById(id);
    };

    public Organization addOrganization(Organization organization){
        return organizationRepository.save(organization);
    }

    public List<Organization> getOrganizations(){
        return organizationRepository.findAll();
    }

    public void deleteOrganizationRepositoryById(int organizationId){
        organizationRepository.delete(organizationId);
    }
}
