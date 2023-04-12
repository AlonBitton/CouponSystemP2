package com.Alon.CouponSystemP2.services;

import com.Alon.CouponSystemP2.entites.Coupon;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class Job extends Services{

    public void run(){
        List<Coupon> deletedCoupon = couponRepo.deleteByEndDateBefore(LocalDate.now());
        if(!deletedCoupon.isEmpty()){
            System.out.println("Deleted Expired Coupons: ");
            deletedCoupon.forEach(System.out::println);
        }

    }

}
