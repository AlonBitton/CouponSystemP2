package com.Alon.CouponSystemP2.services;

import com.Alon.CouponSystemP2.entites.Company;
import com.Alon.CouponSystemP2.entites.Coupon;
import com.Alon.CouponSystemP2.entites.Customer;
import com.Alon.CouponSystemP2.entites.ExceptionMessage;
import com.Alon.CouponSystemP2.exception.CouponSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class AdminService extends Services {


    // Set hard-coded admin credentials
    public static final String adminEmail = "admin@admin.com";
    public static final String adminPassword = "admin";

    public boolean login(String email, String password) {
        return adminEmail.equals(email) && adminPassword.equals(password);
    }


    /**
     * @param company constructor
     * @return companyId
     * @throws CouponSystemException
     * @ checks for Email & Name exist
     */
    public int addCompany(Company company) throws CouponSystemException {
        if (this.companyRepo.existsByEmail(company.getEmail())) {
            throw new CouponSystemException("Error: " + ExceptionMessage.EMAIL_ALREADY_EXIST);
        }
        if (this.companyRepo.existsByName(company.getName())) {
            throw new CouponSystemException("Error: " + ExceptionMessage.EXIST_BY_NAME);
        } else {
            companyRepo.save(company);
            System.out.println("New Company " + company.getName() + " Has been add successfully");
            return company.getId();
        }
    }


    /**
     * @param company constructor
     * @throws CouponSystemException
     * check if company exist by Email. Cannot change Company Name.
     */
    public void updateCompany(Company company) throws CouponSystemException {
        Company updateCompany = companyRepo.findByEmail(company.getEmail())
                .orElseThrow(() -> new CouponSystemException(String.valueOf(ExceptionMessage.COMPANY_NOT_EXIST)));
        if (!this.companyRepo.existsById(company.getId())) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COMPANY_NOT_EXIST);
        }
        if (updateCompany.getName().equals(company.getName())) {
            throw new CouponSystemException(ExceptionMessage.VALUE_CANNOT_BE_CHANGED.getMessage());
        } else {
            companyRepo.save(company);
            System.out.println("Company updated: " + company);
        }
    }


    /**
     * @param companyId int
     * @throws CouponSystemException check if Company exist by ID. delete Company coupons from coupons table.
     *                               Delete Company from companies table
     */
    public void deleteCompany(int companyId) throws CouponSystemException {
        if (!this.companyRepo.existsById(companyId)) {
            throw new CouponSystemException("Error: " + ExceptionMessage.COMPANY_NOT_EXIST);
        } else {
            System.out.println("Company has been deleted: " + customerRepo.findById(companyId));
            couponRepo.deleteByCompany(companyId);
            companyRepo.deleteById(companyId);
        }
    }


    /**
     * @return list of sorted companies by ID.
     */
    public List<Company> getAllCompanies() {
        List<Company> companies = companyRepo.findAll();
        companies.sort(Comparator.comparing(Company::getId));
        companies.forEach(System.out::println);
        return companies;
    }


    /**
     * @param companyId int
     * @return Company
     * @throws CouponSystemException
     * Checks if Company exist
     */
    public Company getOneCompany(int companyId) throws CouponSystemException {
        return this.companyRepo.findById(companyId)
                .orElseThrow(() -> new CouponSystemException("Error: " + ExceptionMessage.COMPANY_NOT_EXIST));
    }


    /**
     * @param customer constructor
     * @throws CouponSystemException
     * check if Email exist
     */
    public void addCustomer(Customer customer) throws CouponSystemException {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new CouponSystemException("Error: " + ExceptionMessage.EMAIL_ALREADY_EXIST);

        } else {
            System.out.println("New Customer " + customer.getFirstName() + " Has been add successfully");
            customerRepo.save(customer);
        }
    }


    /**
     * @param customer contracture
     * @throws CouponSystemException
     * checks if Customer exit by Email and ID.
     * Cannot change Name.
     */
    public void updateCustomer(Customer customer) throws CouponSystemException {
        Customer updateCustomer = customerRepo.findByEmail(customer.getEmail())
                .orElseThrow(() -> new CouponSystemException("Error: " + ExceptionMessage.CUSTOMER_NOT_EXIST));
        if (customer.getId() != updateCustomer.getId()) {
            throw new CouponSystemException("Error: " + ExceptionMessage.VALUE_CANNOT_BE_CHANGED);
        } else {
            System.out.println(customer);
            customerRepo.save(customer);
        }
    }


    /**
     * @param customerId int
     * @throws CouponSystemException
     * checks if Customer exist by ID.
     * delete all Customer purchased Coupons from database.
     * delete Customer from database.
     */
    public void deleteCustomer(int customerId) throws CouponSystemException {
        if (!this.customerRepo.existsById(customerId)) {
            throw new CouponSystemException("Error: " + ExceptionMessage.CUSTOMER_NOT_EXIST);
        } else {
            System.out.println("Customer will be delete now: " + customerRepo.findById(customerId));
            this.couponRepo.deleteCustomerCoupons(customerId);
            this.customerRepo.deleteById(customerId);
        }
    }


    /**
     * @return List of Customers sorted by ID.
     */
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        customers.sort(Comparator.comparing(Customer::getId));
        customers.forEach(System.out::println);
        return customers;
    }


    /**
     * @param customerId int
     * @return Customer
     * @throws CouponSystemException
     * check if Customer exist by ID.
     */
    public Customer getOneCustomer(int customerId) throws CouponSystemException {
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new CouponSystemException("Error: " + ExceptionMessage.CUSTOMER_NOT_EXIST));
    }



}


