package com.middleware.servicemanagement.services;

import com.middleware.servicemanagement.controllers.MessageController;
import com.middleware.servicemanagement.dto.PaymentDto;
import com.middleware.servicemanagement.feign.ServiceInterface;
import com.middleware.servicemanagement.models.Payment;
import com.middleware.servicemanagement.models.ServiceModel;
import com.middleware.servicemanagement.models.UserActivatedServices;
import com.middleware.servicemanagement.producer.RabbitMQProducer;
import com.middleware.servicemanagement.repositories.ServiceRepository;
import com.middleware.servicemanagement.repositories.UserActivatedServicesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceHandlingService {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    UserActivatedServicesRepository userActivatedServicesRepository;

    @Autowired
    ServiceInterface serviceInterface;

//    @Autowired
//    MessageController messageController;

    public ResponseEntity<String> addService(ServiceModel serviceModel) {
        try {
            serviceRepository.save(serviceModel);
            return ResponseEntity.ok("Success");
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateService(Integer serviceId, ServiceModel updatedService) {
        try {
            ServiceModel existingService = serviceRepository.findById(serviceId)
                    .orElseThrow(() -> new EntityNotFoundException("Service not found"));

            existingService.setName(updatedService.getName());
            existingService.setServiceCharge(updatedService.getServiceCharge());

            return new ResponseEntity<>("Successfully updated",HttpStatus.NO_CONTENT);

        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<String> deleteService(Integer serviceId){
        try {
            ServiceModel existingService = serviceRepository.findById(serviceId)
                    .orElseThrow(() -> new EntityNotFoundException("Service not found"));

            serviceRepository.deleteById(serviceId);
            userActivatedServicesRepository.deleteAllByServiceId(serviceId);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Map> getAllServicesRelatedToUser(Integer userId) {
        try {
            List<ServiceModel> services = serviceRepository.findAll();
            List<Integer> userActivatedServices =
                    userActivatedServicesRepository.findAllServicesByUserId(userId);

            Set<Integer> activatedServiceIds = new HashSet<>(userActivatedServices);
            List<ServiceModel> activatedServices = new ArrayList<>();
            List<ServiceModel> notActivatedServices = new ArrayList<>();

            for (ServiceModel service : services) {
                if (activatedServiceIds.contains(service.getServiceId())) {
                    activatedServices.add(service);
                } else {
                    notActivatedServices.add(service);
                }
            }

            Map<String, List> response = new HashMap<>();
            response.put("activatedServices", activatedServices);
            response.put("exploreServices", notActivatedServices);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    public ResponseEntity<String> activateService(PaymentDto paymentDto) {
        try {
            Integer alreadyActivatedServiceId =
                    userActivatedServicesRepository.findActivatedServiceByUserId(
                            paymentDto.getUserId(), paymentDto.getServiceId()
                    );

            if(alreadyActivatedServiceId != null) {
                return new ResponseEntity<>("Already activated", HttpStatus.BAD_REQUEST);
            } else {

                // make payment

                Payment payment =
                        Payment
                                .builder()
                                .userId(paymentDto.getUserId())
                                .serviceId(paymentDto.getServiceId())
                                .amount(paymentDto.getAmount())
                                .build();

                ResponseEntity<Boolean> isPaymentSuccess = serviceInterface.makePayment(payment);
                System.out.println(isPaymentSuccess);

                UserActivatedServices activatedService =
                        UserActivatedServices
                        .builder()
                        .userId(payment.getUserId())
                        .serviceId(paymentDto.getServiceId())
                        .build();

                userActivatedServicesRepository.save(activatedService);

//                messageController.sendMessage("Successfully activated");
                return new ResponseEntity<>("Successfully activated", HttpStatus.CREATED);
            }

        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<String> deactivateService(Integer userId, Integer serviceId) {
        try {
            Integer alreadyActivatedServiceId =
                    userActivatedServicesRepository.findActivatedServiceByUserId(userId, serviceId);

            if(alreadyActivatedServiceId == null) {
                return new ResponseEntity<>("Service is not activated one", HttpStatus.BAD_REQUEST);
            } else {
                userActivatedServicesRepository.deleteActivatedServiceByUserId(userId, serviceId);
                return new ResponseEntity<>("Successfully deactivated", HttpStatus.OK);
            }

        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<List<ServiceModel>> getAllServices() {
        try {
            List<ServiceModel> allServices = serviceRepository.findAll();

            return new ResponseEntity<>(allServices, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
