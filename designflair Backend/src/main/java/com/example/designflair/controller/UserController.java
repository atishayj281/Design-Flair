package com.example.designflair.controller;

import com.example.designflair.model.AuthRequest;
import com.example.designflair.model.AuthResult;
import com.example.designflair.model.User;
import com.example.designflair.service.JwtService;
import com.example.designflair.service.UserService;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/user")
@CrossOrigin(value = {"*"})
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/generateToken")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws NoSuchAlgorithmException {
        ResponseEntity response;
        System.out.println(authRequest);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            long userId = userService.getUserIdByUsername(authRequest.getUsername());
            response = new ResponseEntity(new AuthResult(userId, jwtService.generateToken(authRequest.getUsername()), "Authentication successful"), HttpStatus.OK);
            return response;
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody User user) throws NoSuchAlgorithmException {
        try{
            User result = userService.addUser(user);
            return new ResponseEntity<>(new AuthResult(result.getId(), jwtService.generateToken(result.getUsername()), "Registration successful"), HttpStatus.CREATED);
        } catch (EntityExistsException entityExistsException) {
            return new ResponseEntity<>(entityExistsException.getMessage(), HttpStatus.FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){
        ResponseEntity<?> result;
        User user = userService.getUserById(id);
        if(user == null) {
            result = new ResponseEntity<>("No User Found", HttpStatus.NOT_FOUND);
        } else {
            result = new ResponseEntity<>(user, HttpStatus.OK);
        }
        return result;
    }
}
