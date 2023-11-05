package com.middleware.servicemanagement.feign;

import com.middleware.servicemanagement.models.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("PAYMENT")
public interface ServiceInterface {
    @PostMapping("api/v1/payment")
    public ResponseEntity<Boolean> makePayment(@RequestBody Payment payment);
}
