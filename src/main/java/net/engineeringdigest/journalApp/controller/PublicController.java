package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> addNewUser(@RequestBody UserEntity userObj) {
        String username = userObj.getUserName();
        String password = userObj.getPassword();
        if (!username.isEmpty() && !password.isEmpty()) {
            userService.saveNewUser(userObj);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Insufficient credentials", HttpStatus.BAD_REQUEST);

    }


    @GetMapping("/health-check")
    public String HealthCheck() {
        return "OK";
    }
}
