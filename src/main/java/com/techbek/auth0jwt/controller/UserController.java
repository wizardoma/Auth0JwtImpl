package com.techbek.auth0jwt.controller;

import com.techbek.auth0jwt.model.RegisteredUser;
import com.techbek.auth0jwt.model.User;
import com.techbek.auth0jwt.model.ResetEmailPOJO;
import com.techbek.auth0jwt.model.ResetEmailResponse;
import com.techbek.auth0jwt.repo.UserRepository;
import com.techbek.auth0jwt.service.UserServiceImpl;
import com.techbek.auth0jwt.util.EmailSenderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;
    private UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users/sign-up")
    public void signUp(@RequestBody @Valid RegisteredUser registeredUser) {
        userService.save(registeredUser);
    }

    @GetMapping("/")
    public String home(){
        return "Nothing";
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/users/reset")
    public void sendResetEmail(@RequestBody ResetEmailPOJO resetEmailPOJO){
        userService.resetEmail(resetEmailPOJO);
    }

    @PostMapping("/users/resetconfirm")
    public void changePassword(@RequestBody ResetEmailResponse resetEmailResponse) throws InvalidKeyException {
        userService.changePassword(resetEmailResponse);
    }

}
