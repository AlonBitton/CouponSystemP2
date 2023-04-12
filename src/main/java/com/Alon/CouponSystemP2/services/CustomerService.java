package com.Alon.CouponSystemP2.services;

import com.Alon.CouponSystemP2.entites.Coupon;
import com.Alon.CouponSystemP2.entites.Customer;
import com.Alon.CouponSystemP2.entites.ExceptionMessage;
import com.Alon.CouponSystemP2.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class CustomerService extends Services {

    @Autowired
    private CouponService couponService;

    public boolean login(String email, String password) {
        return customerRepo.existsByEmailAndPassword(email, password);
    }


    /**
     * @param customerId int
     * @param couponId int
     * @throws CouponSystemException
     * Checks if Coupon exist by ID
     * Checks if Coupon amount == 0
     * Checks if Coupon expired
     * Checks if Coupon already purchased
     * Deduct from coupon amount (-1) after purchase
     */
    public void purchaseCoupon(int customerId, int couponId) throws CouponSystemException {
        if (!couponRepo.existsById(couponId)) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COUPON_NOT_EXIST);
        } else if (couponService.checkAmount(couponId) == 0) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COUPON_AMOUNT_IS_ZERO);
        } else if (couponService.checkExpirationDate(couponId).isBefore(LocalDate.now())) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COUPON_EXPIRED);
        } else if (couponRepo.checkIfHaveCoupon(customerId, couponId) == 1) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COUPON_ALREADY_PURCHASED);
        } else {
            customerRepo.addCouponPurchase(customerId, couponId);
            couponRepo.findById(couponId).get().setAmount(couponRepo.findById(couponId).get().getAmount() - 1);
            System.out.println("Coupon " + couponId + " Has been purchased successfully");
        }
    }




    /**
     * @param customerId int
     * @return List of Customer Coupons sorted by ID.
     * @throws CouponSystemException
     * checks if Customer exist by ID/
     */
    public List<Coupon> getCustomerCoupons(int customerId) throws CouponSystemException {
        if (!customerRepo.existsById(customerId)) {
            throw new CouponSystemException(String.valueOf(ExceptionMessage.CUSTOMER_NOT_EXIST));
        }
        List<Coupon> customerCoupons = this.couponRepo.findByCustomerId(customerId);
        customerCoupons.sort(Comparator.comparing(Coupon::getId));
        customerCoupons.forEach(System.out::println);
        return customerCoupons;
    }


    /**
     * @param customerId int
     * @param category Category
     * @return List of Customer Coupons by the same Category, sorted by Category.
     * @throws CouponSystemException
     * check is customer exist by ID.
     */
    public List<Coupon> getCouponsByCategoryAndCustomerId(int customerId, String category) throws CouponSystemException {
        if (!customerRepo.existsById(customerId)) {
            throw new CouponSystemException(String.valueOf(ExceptionMessage.CUSTOMER_NOT_EXIST));
        }
        List<Coupon> customerCoupons = this.couponRepo.getCouponsByCustomerIdAndCategory(customerId, category);
        customerCoupons.sort(Comparator.comparing(Coupon::getCategory));
        customerCoupons.forEach(System.out::println);
        return customerCoupons;
    }

    /**
     * @param customerId int
     * @param price double
     * @return List of Customer Coupons by Price <= <Price> sorted by Price.
     * @throws CouponSystemException
     * checks if customer exist by ID.
     */
    public List<Coupon> getCustomerIdAndPriceLessThanEqual(int customerId, double price) throws CouponSystemException {
        if (!customerRepo.existsById(customerId)) {
            throw new CouponSystemException(String.valueOf(ExceptionMessage.CUSTOMER_NOT_EXIST));
        }
        List<Coupon> customerCoupons = this.couponRepo.getCustomerCouponsByPrice(customerId, price);
        customerCoupons.sort(Comparator.comparing(Coupon::getPrice));
        customerCoupons.forEach(System.out::println);
        return customerCoupons;
    }


    /**
     * @param customerId int
     * @return Customer
     * @throws CouponSystemException
     * checks if Customer exist by ID
     */
    public Customer getOneCustomer(int customerId) throws CouponSystemException {
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new CouponSystemException("Error: " + ExceptionMessage.CUSTOMER_NOT_EXIST));
    }


    /**
     * @param customerEmail String
     * @return Customer
     * @throws CouponSystemException
     * checks if Customer exist by Email
     */
    public Customer getCustomerByEmail(String customerEmail) throws CouponSystemException {
        return customerRepo.findByEmail(customerEmail)
                .orElseThrow(() -> new CouponSystemException("Error: " + ExceptionMessage.CUSTOMER_NOT_EXIST));
    }


}
