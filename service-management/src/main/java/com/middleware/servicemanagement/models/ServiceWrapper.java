package com.middleware.servicemanagement.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceWrapper {
    private Integer id;
    private String name;
    private boolean activated;
}
