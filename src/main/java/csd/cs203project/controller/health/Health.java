package csd.cs203project.controller.health;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class Health {
    @GetMapping("/health")    
    public ResponseEntity getHealthCheck(){
        return ResponseEntity.ok("instance OK");
    }
}