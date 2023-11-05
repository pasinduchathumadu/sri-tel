package com.example.demo.entity;



import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotBlank;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

//    @Enumerated(EnumType.STRING)
//    public Role role;




}
