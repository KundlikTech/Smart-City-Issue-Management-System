package com.smartcity.service;

import com.smartcity.dto.MapEvent;
import com.smartcity.dto.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Broadcast to all connected subscribers of /topic/assignments
     */
    public void broadcastAssignment(Notification notification) {
        messagingTemplate.convertAndSend("/topic/assignments", notification);
    }

    /**
     * Send to a specific user (Spring will map user via Principal name)
     * the destination becomes /user/{username}/queue/assignments
     */
    public void sendToUser(String username, Notification notification) {
        messagingTemplate.convertAndSendToUser(username, "/queue/assignments", notification);
    }

    public void broadcastStatusUpdate(Notification n) {
    messagingTemplate.convertAndSend("/topic/status", n);
}
public void broadcastMapEvent(MapEvent event) {
        messagingTemplate.convertAndSend("/topic/map", event);
    }

    // Send map event to a specific user (e.g. officer)
    public void sendMapEventToUser(String username, MapEvent event) {
        messagingTemplate.convertAndSendToUser(username, "/queue/map", event);
    }

}
