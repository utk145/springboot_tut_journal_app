package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String test() {
        return "Hey";
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> listOfAllUsers = userService.getAll();
        if (listOfAllUsers != null && !listOfAllUsers.isEmpty()) {
            return new ResponseEntity<>(listOfAllUsers, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


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

    @PutMapping("/update-user/{newUserName}")
    public ResponseEntity<?> updateUser(
            @RequestBody UserEntity userObj,
            @PathVariable String newUserName
    ) {
        UserEntity userInDb = userService.findByUserName(userObj.getUserName());
        if (userInDb != null) {
            userInDb.setUserName(newUserName);
            userInDb.setPassword(userObj.getPassword());
            userService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
