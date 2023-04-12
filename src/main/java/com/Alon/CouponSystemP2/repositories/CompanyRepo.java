package com.Alon.CouponSystemP2.repositories;

import com.Alon.CouponSystemP2.entites.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
    boolean existsByName(String name);
    boolean existsByEmailAndPassword(String email, String password);
    Optional<Company> findByEmail(String email);
    boolean existsByEmail(String email);





}
