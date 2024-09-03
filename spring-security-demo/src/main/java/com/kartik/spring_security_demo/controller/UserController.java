package com.kartik.spring_security_demo.controller;

import com.kartik.spring_security_demo.JwtService;
import com.kartik.spring_security_demo.model.User;
import com.kartik.spring_security_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService ser;

    @Autowired
     AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("register")
    public User register(@RequestBody User user){
        return ser.saveUser(user);
    }

    @PostMapping("login")
    public String login(@RequestBody User user)
    {
//        inbuilt in spring to chk in mern directly with findone
//        chk pass asd userame that pass i ody
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
//if sucess the geerate toke
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }else{
            return "Login failed";

        }

    }

}
