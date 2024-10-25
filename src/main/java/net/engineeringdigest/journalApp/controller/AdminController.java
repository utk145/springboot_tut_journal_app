package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

    @GetMapping("/list-all-users")
    public ResponseEntity<List<UserEntity>> listAllUsers() {
        List<UserEntity> allUsers = userService.getAll();
        if (allUsers != null && !allUsers.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(allUsers);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping(value = "/grantAdminAccess")
    public ResponseEntity<String> grantAdminAccess(@RequestBody String userName) {
        System.out.println(userName);
        //////////////////////////////////// #STRUCK #STRUCK ///////////////////////////////////////////////////
        UserEntity user = userService.findByUserName(userName);
        if (user != null) {
            userService.grantAdminRole(user);
            return ResponseEntity.ok("Admin access granted to user: " + userName);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + userName);
    }


}
