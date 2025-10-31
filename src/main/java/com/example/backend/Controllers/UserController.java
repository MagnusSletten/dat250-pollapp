package com.example.backend.Controllers;

import java.util.Collection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend.Managers.PollManager;
import com.example.backend.Model.User.User;
import com.example.backend.Model.User.User.Roles;
import com.example.backend.Model.User.UserDTO;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    PollManager pollManager;

    public UserController(PollManager pollManager) {
        this.pollManager = pollManager;
    }

    @GetMapping("/{userName}")
    public User getUser(@PathVariable("userName") String userName) {
        return pollManager.getUser(userName).get();
    }

    @GetMapping("/me/role")
    public Roles getMyRole(Authentication auth) {
        return pollManager.getUser(auth.getName())
                        .map(User::getRole)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody UserDTO userRequest) {

        try {
            User user = pollManager.addUserFromRequest(userRequest);
            if (user.getRole() == null) {
                user.setRole(User.Roles.NORMAL);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();

        }
    }

    @GetMapping
    public Collection<User> getUsers() {
        return pollManager.getUsers();
    }
}
