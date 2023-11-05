package com.middleware.servicemanagement.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PaymentDto {
    private Integer userId;
    private Integer serviceId;
    private Double amount;
}
