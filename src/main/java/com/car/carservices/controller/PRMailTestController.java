// src/main/java/com/car/carservices/controller/PRMailTestController.java
package com.car.carservices.controller;

import com.car.carservices.service.PRNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class PRMailTestController {
    private final PRNotificationService notify;
    public PRMailTestController(PRNotificationService notify) { this.notify = notify; }

    @PostMapping("/mail")
    public ResponseEntity<String> send(@RequestParam String to, @RequestParam String code) {
        notify.sendEmailCode(to, code, "[TEST]");
        return ResponseEntity.ok("Sent to " + to);
    }
}
