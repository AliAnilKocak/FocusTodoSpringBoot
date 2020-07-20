package com.cale.focustodo.repository;

import com.cale.focustodo.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Integer> {
    ApplicationUser findByUsername(String username);
}
