package com.exam.examserver.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.examserver.config.JwtTokenHelper;
import com.exam.examserver.entity.JwtRequest;
import com.exam.examserver.entity.User;
import com.exam.examserver.service.impl.UserDetailsServiceImpl;

@RestController
@CrossOrigin("*")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;


    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
        try {
            this.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (UsernameNotFoundException e) {
            throw new Exception("User not found");
        }

        //authenticate
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token = this.jwtTokenHelper.generateToken(userDetails);
        return ResponseEntity.ok(Map.of("token", token));
    }

    public void authenticate(String username, String password) throws Exception{

        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User Disabled");
        }catch (BadCredentialsException e){
            throw new Exception("Invalid Credentials");
        }
    }

    //returns the details of the current user
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal) {
        return ((User)this.userDetailsService.loadUserByUsername(principal.getName()));
    }
}
