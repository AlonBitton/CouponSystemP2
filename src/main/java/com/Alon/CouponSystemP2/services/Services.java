package com.Alon.CouponSystemP2.services;

import com.Alon.CouponSystemP2.repositories.CompanyRepo;
import com.Alon.CouponSystemP2.repositories.CouponRepo;
import com.Alon.CouponSystemP2.repositories.CustomerRepo;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public abstract class Services{

    @Autowired
    protected CompanyRepo companyRepo;
    @Autowired
    protected CouponRepo couponRepo;
    @Autowired
    protected CustomerRepo customerRepo;

    /**
     * @param email
     * @param password
     * @return boolean (true false)
     * Login method for clients
     */
    public boolean login(String email, String password){
        return false;
    }

}
