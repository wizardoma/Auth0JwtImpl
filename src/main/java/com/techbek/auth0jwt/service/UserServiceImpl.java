package com.techbek.auth0jwt.service;

import com.techbek.auth0jwt.model.RegisteredUser;
import com.techbek.auth0jwt.model.ResetEmailPOJO;
import com.techbek.auth0jwt.model.ResetEmailResponse;
import com.techbek.auth0jwt.model.User;
import com.techbek.auth0jwt.repo.UserRepository;
import com.techbek.auth0jwt.util.EmailSenderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.security.InvalidKeyException;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    final EmailSenderUtil emailSenderUtil;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, EmailSenderUtil emailSenderUtil) {
        this.userRepository = userRepository;
        this.emailSenderUtil = emailSenderUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), emptyList());
    }


    public void save(RegisteredUser registeredUser) {
        log.info("In sign up page");
        User user = new User();
        user.setFirstName(registeredUser.getFirstName());
        user.setEmail(registeredUser.getEmail());
        user.setUsername(registeredUser.getUsername());
        user.setSecondName(registeredUser.getSecondName());
        user.setPassword(bCryptPasswordEncoder.encode(registeredUser.getPassword()));
        userRepository.save(user);
        emailSenderUtil.sendWelcomeEmail(user.getEmail());
    }

    public void resetEmail(ResetEmailPOJO resetEmailPOJO) {
        User user = userRepository.findByEmail(resetEmailPOJO.getEmail());
        if (user==null){
            throw new UsernameNotFoundException("Email Address not found");
        }
        String uuid= UUID.randomUUID().toString();
        log.info(uuid);
        user.setResetEmailToken(uuid);
        userRepository.save(user);
        log.info("saved token in user account");
        emailSenderUtil.sendResetToken(user.getEmail(),uuid);
        log.info("email sent");
    }

    public void changePassword(ResetEmailResponse resetEmailResponse) throws InvalidKeyException {
        User user = userRepository.findByEmail(resetEmailResponse.getEmail());
        if (user==null){
            throw new UsernameNotFoundException("Invalid Email Address");
        }
        log.info(user.getResetEmailToken()+" and "+resetEmailResponse.getUuid());
        if (!user.getResetEmailToken().equals(resetEmailResponse.getUuid())){
            throw new InvalidKeyException();
        }

        user.setPassword(resetEmailResponse.getNewpassword());
        userRepository.save(user);
        log.info("new password saved");
    }
}
