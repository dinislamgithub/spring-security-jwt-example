package com.din.jwt.api.controller;

import com.din.jwt.api.entity.AuthRequest;
import com.din.jwt.api.entity.User;
import com.din.jwt.api.repository.UserRepository;
import com.din.jwt.api.util.JwtUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;

    @GetMapping("/")
    public String welcome() {
        return "Welcome to spring security jwt exmaple !!";
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUserName());
    }
    
    @GetMapping("/getUserByUsername/{userName}")
    public User getUserByUsername(@PathVariable("userName") String userName) {    	
        return repository.findByUserName(userName);
    }
    
    @GetMapping("/getUsers")
    public List<User> getUser() {    	
        return repository.findAll();
    }
    
}
