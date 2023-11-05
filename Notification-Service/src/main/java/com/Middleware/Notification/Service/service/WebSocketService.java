package com.Middleware.Notification.Service.service;

import com.Middleware.Notification.Service.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void getNotification(String notification) {

    }
}
