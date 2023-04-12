package com.Alon.CouponSystemP2.repositories;

import com.Alon.CouponSystemP2.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    boolean existsByEmailAndPassword(String email, String password);
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);

    @Modifying
    @Query(value = "INSERT INTO customers_vs_coupons (customer_id, coupon_id) VALUES (:customerId, :couponId)", nativeQuery = true)
    void addCouponPurchase(int customerId, int couponId);


}
