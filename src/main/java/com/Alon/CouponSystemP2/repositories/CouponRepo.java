package com.Alon.CouponSystemP2.repositories;

import com.Alon.CouponSystemP2.entites.Category;
import com.Alon.CouponSystemP2.entites.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {

    @Transactional
    @Modifying
    List<Coupon> deleteByEndDateBefore(LocalDate endDate);

    @Query("select c from Coupon c where c.title = ?1")
    Optional<Coupon> optFindByTitle(String title);

    void deleteByCompany(int company);

    Coupon findByTitle(String title);

    List<Coupon> findByCompany(int company);

    boolean existsByTitle(String title);

    @Query(value = "SELECT coupons.* FROM customers_vs_coupons JOIN coupons ON customers_vs_coupons.coupon_id = coupons.id WHERE customer_id = :customerId", nativeQuery = true)
    List<Coupon> findByCustomerId(int customerId);

    @Modifying
    @Query(value = "DELETE FROM customers_vs_coupons WHERE coupon_id = :couponId", nativeQuery = true)
    void deleteCouponPurchase(int couponId);

    @Modifying
    @Query(value = "DELETE FROM customers_vs_coupons WHERE customer_id = :customerId", nativeQuery = true)
    void deleteCustomerCoupons(int customerId);

    @Query(value = "SELECT count(*) FROM customers_vs_coupons WHERE customer_id = :customerId AND coupon_id = :couponId", nativeQuery = true)
    int checkIfHaveCoupon(int customerId, int couponId);

    List<Coupon> findByCompanyAndPriceLessThanEqual(int company, double Price);

    List<Coupon> getCouponsByCompanyAndCategory(int company, Category category);

    @Query(value = "SELECT coupons.* FROM customers_vs_coupons JOIN coupons ON customers_vs_coupons.coupon_id = coupons.id WHERE customer_id = :customerId AND coupons.price < :price", nativeQuery = true)
    List<Coupon> getCustomerCouponsByPrice(int customerId, double price);

    @Query(value = "SELECT coupons.* FROM customers_vs_coupons JOIN coupons ON customers_vs_coupons.coupon_id = coupons.id WHERE customer_id = :customerId AND coupons.category = :category", nativeQuery = true)
    List<Coupon> getCouponsByCustomerIdAndCategory(int customerId, String category);


}
