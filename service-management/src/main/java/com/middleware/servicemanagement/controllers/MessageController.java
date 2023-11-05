package com.middleware.servicemanagement.controllers;

import com.middleware.servicemanagement.producer.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
    private RabbitMQProducer producer;

//    public MessageController(RabbitMQProducer producer){
//        this.producer = producer;
//    }

    @GetMapping("/publish")
    public void sendMessage(@RequestParam("message") String message){
        producer.sendMessage(message);
    }
}
