package com.Middleware.Notification.Service.web;

import com.Middleware.Notification.Service.dao.NotificationRepository;
import com.Middleware.Notification.Service.entity.Notification;
import com.Middleware.Notification.Service.exceptions.ResourceNotFoundException;
import com.Middleware.Notification.Service.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@RestController
@EnableWebSocketMessageBroker
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationRepository notificationRepository;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public List<Notification> greet() throws InterruptedException {
        Thread.sleep(2000);

        return notificationRepository.findAll();
//        return new Greeting("Hello, " +
//                HtmlUtils.htmlEscape(message.getName()));
    }

//    @MessageMapping("/notification")
//    @SendTo("/topic/notification")
//    @Scheduled(fixedDelay = 5000)
//    public void sendNotification() {
//        messagingTemplate.convertAndSend("/topic/notification", "Hello from Spring Boot!");
//        System.out.println("Notification sent!");
//    }


//    @MessageMapping("/notification")
//    @SendTo("/topic/notification")

//    @Scheduled(fixedDelay = 5000)
//    public void sendNotification() {
//        messagingTemplate.convertAndSend("/topic/notification", "Hello from Spring Boot!");
//        System.out.println("Notification sent!");
//    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
//
    @PostMapping
    public Notification createNotification(@RequestBody Notification notification) throws InterruptedException {
        Notification save = notificationRepository.save(notification);
        this.greet();
        Thread.sleep(2000);
        return save;

    }
//
//    @GetMapping("/{id}")
//    public Notification getNotificationById(@PathVariable Long id) throws ResourceNotFoundException {
//        return notificationRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id));
//    }
//
//    @PutMapping("/{id}")
//    public Notification updateNotification(@PathVariable Long id, @RequestBody Notification notificationDetails) throws ResourceNotFoundException {
//        Notification notification = notificationRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id));
//
//        notification.setType(notificationDetails.getType());
//        notification.setMessage(notificationDetails.getMessage());
//
//        return notificationRepository.save(notification);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteNotification(@PathVariable Long id) throws ResourceNotFoundException {
//        Notification notification = notificationRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id " + id));
//
//        notificationRepository.delete(notification);
//
//        return ResponseEntity.ok().build();
//    }
}
