package com.middleware.billing.service;

import com.middleware.billing.model.Payment;
import com.middleware.billing.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BillingService {

    @Autowired
    PaymentRepository paymentRepository;
    public Boolean makePayment(Payment payment) {

        try {
            paymentRepository.save(payment);
            return true;
        } catch (Exception exception) {
            return false;
        }

    }
}
