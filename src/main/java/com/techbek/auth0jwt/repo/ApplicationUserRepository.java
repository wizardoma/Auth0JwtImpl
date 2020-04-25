package com.techbek.auth0jwt.repo;

import com.techbek.auth0jwt.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository  extends JpaRepository<ApplicationUser, Long> {

    ApplicationUser findByUsername(String username);
}
