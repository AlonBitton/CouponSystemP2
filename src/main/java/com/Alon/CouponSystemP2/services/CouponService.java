package com.Alon.CouponSystemP2.services;

import com.Alon.CouponSystemP2.entites.Category;
import com.Alon.CouponSystemP2.entites.ClientType;
import com.Alon.CouponSystemP2.entites.Coupon;
import com.Alon.CouponSystemP2.entites.ExceptionMessage;
import com.Alon.CouponSystemP2.exception.CouponSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CouponService extends Services {

    public int addCoupon(Coupon coupon) throws CouponSystemException {
        if (this.couponRepo.existsByTitle(coupon.getTitle())) {
            throw new CouponSystemException("Error: " + ExceptionMessage.TITLE_ALREADY_EXIST);
        } else {
            couponRepo.save(coupon);
            System.out.println("New Coupon has been added: " + coupon);
            return coupon.getId();
        }
    }


    public int checkAmount(int couponId) throws CouponSystemException {
        if (!this.couponRepo.existsById(couponId)) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COUPON_NOT_EXIST);
        } else {
            return this.couponRepo.findById(couponId).get().getAmount();
        }
    }

    public LocalDate checkExpirationDate(int couponId) throws CouponSystemException {
        if (!this.couponRepo.existsById(couponId)) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COUPON_NOT_EXIST);
        } else {
            return this.couponRepo.findById(couponId).get().getEndDate();
        }
    }


    public void purchaseCoupon(int customerId, int couponId) throws CouponSystemException {
        if (!couponRepo.existsById(couponId)) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COUPON_NOT_EXIST);
        } else if (checkAmount(couponId) == 0) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COUPON_AMOUNT_IS_ZERO);
        } else if (checkExpirationDate(couponId).isBefore(LocalDate.now())) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COUPON_EXPIRED);
        } else if (this.couponRepo.checkIfHaveCoupon(customerId, couponId) == 1) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COUPON_ALREADY_PURCHASED);
        } else {
            customerRepo.addCouponPurchase(customerId, couponId);
            couponRepo.findById(couponId).get().setAmount(couponRepo.findById(couponId).get().getAmount() - 1);
            System.out.println("Coupon " + couponId + " Has been purchased successfully");
        }
    }

}