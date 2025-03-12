package com.azilen.training.repository;

import com.azilen.training.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails,Integer> {
    UserDetails findByEmail(String email);
}
