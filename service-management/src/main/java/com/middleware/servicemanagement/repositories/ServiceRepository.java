package com.middleware.servicemanagement.repositories;

import com.middleware.servicemanagement.models.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel, Integer> {

}
