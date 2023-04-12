package com.Alon.CouponSystemP2.services;

import com.Alon.CouponSystemP2.entites.*;
import com.Alon.CouponSystemP2.exception.CouponSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class CompanyService extends Services {

    public boolean login(String email, String password) {
        return companyRepo.existsByEmailAndPassword(email, password);
    }


    /**
     * @param coupon Constructor
     * @throws CouponSystemException
     * checks if title already exist, in case exist checks if the Coupon owned by the same Company.
     *               if yes throw exception, else add new Coupon.
     */
    public void addCoupon(Coupon coupon) throws CouponSystemException {
        if (this.couponRepo.existsByTitle(coupon.getTitle())) {
            Coupon newCoupon = couponRepo.findByTitle(coupon.getTitle());
            if(coupon.getTitle().equals(newCoupon.getTitle()) && coupon.getCompany() == newCoupon.getCompany()){
            throw new CouponSystemException("Error: " + ExceptionMessage.TITLE_ALREADY_EXIST);
            }
        } else {
            couponRepo.save(coupon);
            System.out.println("New Coupon has been added: " + coupon);
        }
    }

    /**
     * @param coupon constructor
     * @throws CouponSystemException
     * check if exist by title. Cannot change Company / ID.
     */
    public void updateCoupon(Coupon coupon) throws CouponSystemException {
        Coupon updatCoupon = couponRepo.optFindByTitle(coupon.getTitle())
                .orElseThrow(() -> new CouponSystemException("Error: " + ExceptionMessage.COUPON_NOT_EXIST));
        if (updatCoupon.getCompany() != coupon.getCompany()) {
        throw new CouponSystemException(ExceptionMessage.VALUE_CANNOT_BE_CHANGED.getMessage());
        } else {
            couponRepo.save(coupon);
            System.out.println("Coupon updated: " + coupon);
        }
    }


    /**
     * @param couponId int
     * @throws CouponSystemException
     * Checks if coupon exist by ID. delete coupon purchase from database
     * delete Coupon from coupons database.
     */
    public void deleteCoupon(int couponId) throws CouponSystemException {
        if (!this.couponRepo.existsById(couponId)) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COUPON_NOT_EXIST);
        } else {
            this.couponRepo.deleteCouponPurchase(couponId);
            this.couponRepo.deleteById(couponId);
            System.out.println("The Coupon has been deleted " + couponId);
        }
    }

    /**
     * @param companyId int
     * @return List of all Company Coupons.
     * @throws CouponSystemException
     * checks if Company exist by ID.
     */
    public List<Coupon> getCompanyAllCoupons(int companyId) throws CouponSystemException {
        if (!companyRepo.existsById(companyId)) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COMPANY_NOT_EXIST);
        }
        List<Coupon> coupons = couponRepo.findByCompany(companyId);
        coupons.forEach(System.out::println);
        return coupons;
    }


    /**
     * @param companyId int
     * @param category Category
     * @return List of all Company Coupons with the same Category sorted by Category.
     * @throws CouponSystemException
     * checks if Company exist by ID.
     */
    public List<Coupon> getCouponsByCompanyIdAndCategory(int companyId, Category category) throws CouponSystemException {
        if (!companyRepo.existsById(companyId)) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COMPANY_NOT_EXIST);
        } else {
            return couponRepo.getCouponsByCompanyAndCategory(companyId, category);
        }

    }

    /**
     * @param companyId int
     * @param price double
     * @return List of all Company Coupons by Price limit sorted by Price.
     * @throws CouponSystemException
     * checks if Company exist by ID.
     */
    public List<Coupon> findByCompanyIdAndPriceLessThanEqual(int companyId, double price) throws CouponSystemException {
        if (!companyRepo.existsById(companyId)) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COMPANY_NOT_EXIST);
        } else {
            List<Coupon> coupons = this.couponRepo.findByCompanyAndPriceLessThanEqual(companyId, price);
            coupons.sort(Comparator.comparing(Coupon::getPrice));
            coupons.forEach(System.out::println);
            return coupons;

        }
    }


    /**
     * @param companyId int
     * @return Company
     * @throws CouponSystemException
     * Checks if Company exist by ID.
     */
    public Company getOneCompany(int companyId) throws CouponSystemException {
        return this.companyRepo.findById(companyId)
                .orElseThrow(() -> new CouponSystemException("Error: " + ExceptionMessage.COMPANY_NOT_EXIST));
    }


    /**
     * @param couponId int
     * @return Coupon
     * @throws CouponSystemException
     * checks if Coupon exist by ID
     */
    public Coupon getOneCoupon(int couponId) throws CouponSystemException {
        return this.couponRepo.findById(couponId)
                .orElseThrow(() -> new CouponSystemException("Error: " + ExceptionMessage.COUPON_NOT_EXIST));
    }


    /**
     * @param companyEmail String
     * @return Company
     * @throws CouponSystemException
     * checks if Company exist by email
     */
    public Company getCompanyByEmail(String companyEmail) throws CouponSystemException {
        return companyRepo.findByEmail(companyEmail)
                .orElseThrow(() -> new CouponSystemException("Error: " + ExceptionMessage.COMPANY_NOT_EXIST));
    }

}