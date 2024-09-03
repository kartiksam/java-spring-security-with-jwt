package com.kartik.spring_security_demo.service;

import com.kartik.spring_security_demo.dao.UserRepo;
import com.kartik.spring_security_demo.model.User;
import com.kartik.spring_security_demo.model.UserDetailImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

@Autowired
private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user= repo.findByUsername(username);
       if(user==null){
           System.out.println("User 404");
           throw new UsernameNotFoundException("User 404");
       }
//       how to return UserDetyails we will implemt this with usertdetailimpl ansd willl rerturn that
       return new UserDetailImpl(user);
    }
}
