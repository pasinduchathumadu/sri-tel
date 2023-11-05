package com.middleware.billing.controller;

import com.middleware.billing.model.Payment;
import com.middleware.billing.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class BillingController {

    @Autowired
    BillingService billingService;

    @PostMapping
    public Boolean makePayment(@RequestBody Payment payment) {
        return billingService.makePayment(payment);
    }
}
