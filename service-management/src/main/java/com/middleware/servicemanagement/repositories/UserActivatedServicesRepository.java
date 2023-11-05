package com.middleware.servicemanagement.repositories;

import com.middleware.servicemanagement.models.UserActivatedServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserActivatedServicesRepository extends JpaRepository<UserActivatedServices, Integer> {

    @Query(value = "SELECT u.serviceId FROM user_activated_services u " +
            "WHERE u.user_id = ?1", nativeQuery = true)
    List<Integer> findAllServicesByUserId(Integer userId);

    @Query(value = "SELECT * FROM user_activated_services " +
            "WHERE user_id = ?1 and service_id = ?2", nativeQuery = true)
    Integer findActivatedServiceByUserId(Integer userId, Integer serviceId);

    @Query(value = "DELETE FROM user_activated_services u " +
            "WHERE u.user_id = ?1 and u.service_id = ?2", nativeQuery = true)
    Integer deleteActivatedServiceByUserId(Integer userId, Integer serviceId);

    @Query(value = "DELETE FROM user_activated_services " +
            "WHERE service_id = ?1", nativeQuery = true)
    void deleteAllByServiceId(Integer serviceId);
}
