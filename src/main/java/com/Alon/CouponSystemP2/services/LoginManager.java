package com.Alon.CouponSystemP2.services;

import com.Alon.CouponSystemP2.entites.ClientType;
import com.Alon.CouponSystemP2.entites.ExceptionMessage;
import com.Alon.CouponSystemP2.exception.CouponSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {


    // !
    @Autowired
    private ApplicationContext ctx;

    /**
     * @param email
     * @param password
     * @param clientType
     * @return
     * If login successfully return client service for the logged in client type.
     * Math Administrator to hard-coded credentials
     * Math the rest to database information.
     * @throws CouponSystemException
     */
    public Services login(String email, String password, ClientType clientType) throws CouponSystemException {


        if (clientType == ClientType.Administrator) {
            AdminService adminService = ctx.getBean(AdminService.class);
            if (adminService.login(email, password)) {
                return adminService;
            }
        } else if (clientType == ClientType.Company) {
            CompanyService companyService = ctx.getBean(CompanyService.class);
            if (companyService.login(email, password)) {
                return companyService;
            }
        } else if (clientType == ClientType.Customer) {
            CustomerService customerService = ctx.getBean(CustomerService.class);
            if (customerService.login(email, password)) {
                return customerService;
            }
        }
        throw new CouponSystemException(String.valueOf(ExceptionMessage.AUTHENTICATION_FAILED.getMessage()));
    }
}
