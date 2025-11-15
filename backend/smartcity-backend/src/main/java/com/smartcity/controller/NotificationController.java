package com.smartcity.controller;

import com.smartcity.model.Notification;
import com.smartcity.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * ✅ Get all notifications of a user (sorted by date desc)
     * GET /api/notifications/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable Long userId) {
        List<Notification> list = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return ResponseEntity.ok(list);
    }

    /**
     * ✅ Mark a notification as READ
     * PUT /api/notifications/{id}/read
     */
    @PutMapping("/{id}/read")
    public ResponseEntity<String> markAsRead(@PathVariable Long id) {
        return notificationRepository.findById(id)
                .map(n -> {
                    n.setRead(true);
                    notificationRepository.save(n);
                    return ResponseEntity.ok("Notification marked as read");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * ❗ Optional: Mark ALL notifications as read for a user
     * PUT /api/notifications/user/{userId}/read-all
     */
    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<String> markAllAsRead(@PathVariable Long userId) {
        List<Notification> list = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);

        list.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(list);

        return ResponseEntity.ok("All notifications marked as read");
    }
}
