package com.yagsog.api.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

@RestController
@Transactional
public class UserController {
    private UserService userService;

    @PostMapping("/api/name")
    public ResponseEntity<String> updateName(@RequestBody String name) {
        return ResponseEntity.ok("Success");

    }

    @PostMapping("/api/age")
    public ResponseEntity<String> updateAge(@RequestBody String name) {
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/api/gender")
    public ResponseEntity<String> updateGender(@RequestBody String name) {
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/api/medications")
    public ResponseEntity<String> updateMedications(@RequestBody String name) {
        return ResponseEntity.ok("Success");
    }


}
