package com.Alon.CouponSystemP2;

import com.Alon.CouponSystemP2.entites.*;
import com.Alon.CouponSystemP2.exception.CouponSystemException;
import com.Alon.CouponSystemP2.services.*;
import org.junit.Test;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@EnableScheduling
@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(OrderAnnotation.class)
public class CouponSystemP2ApplicationTests {

    @Autowired
    Job job = new Job();
    @Autowired
    private LoginManager loginManager;
    // The JOB Scheduled to run as long as the program is running. No need to stop the job manually it will stop automatically.
    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 5) // set for 5 seconds for testing
    /*@Scheduled(cron = "1 0 * * *") // unComment this ine and Comment the line above to run every 24h (every day at 00:01)*/
    public void Job(){
    // To see the job work search for < Deleted Expired Coupons > in console
        job.run();
    }

    @Test
    @Order(1) // run before each Test()
    public void couponSystemUtil() throws Throwable {

        System.out.println("============ Setup Accounts! ============");
        AdminService adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.Administrator);

        // Add Managing company for testing
        Company company = new Company();
        System.out.println();
        company.setEmail("company@company.com");
        company.setPassword("company");
        company.setName("Test Company");
        catchAndCheckException(CouponSystemException.class, () -> adminService.addCompany(company));

        // Add Managing customer for testing
        System.out.println();
        Customer customer = new Customer();
        customer.setEmail("Testcustomer@customer.com");
        customer.setPassword("customer");
        customer.setFirstName("Test Customer");
        catchAndCheckException(CouponSystemException.class, () -> adminService.addCustomer(customer));

    }

    @Test()
    @Order(2)
    public void adminTest() throws Throwable {
        System.out.println("============ Admin Test ============");
        AdminService adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.Administrator);

        // Add random x20 Company
        System.out.println("++++++++++ Expected Exception - Add random x20 Company's ++++++++++");
        for (int i = 1; i <= 10; i++) {
            Company company = new Company();
            company.setName("Company " + i);
            company.setEmail("company" + i + "@test.com");
            company.setPassword("password" + i);
            catchAndCheckException(CouponSystemException.class, () -> adminService.addCompany(company));
        }

        /* Update Company */
        System.out.println();
        Company company = new Company();
        company.setName("UpdatedCompany");
        company.setEmail("company1@test.com");
        company.setPassword("CompanyUpdated");
        catchAndCheckException(CouponSystemException.class,
                () -> adminService.updateCompany(company));







        /* Update Company with exists by Email */
        System.out.println();
        System.out.println("++++++++++ Expected Exception - Update Company with exists by Email ++++++++++");
        Company company2 = new Company();
        company2.setName("UpdatedCompany");
        company2.setEmail("updatedCompany@company");
        company2.setPassword("Company");
        catchAndCheckException(CouponSystemException.class, () -> adminService.updateCompany(company2));

        // Delete Company
        System.out.println();
        catchAndCheckException(CouponSystemException.class, () -> adminService.deleteCompany(5));


        // Try to Delete Company that's not exists
        System.out.println();
        System.out.println("++++++++++ Expected Exception - Try to Delete Company that's not exists ++++++++++");
        catchAndCheckException(CouponSystemException.class, () -> adminService.deleteCompany(5));

        // Print Company(2)
        System.out.println();
        catchAndCheckException(CouponSystemException.class, () -> System.out.println(adminService.getOneCompany(2)));


        // Print Company that's not exists(1
        System.out.println();
        System.out.println("++++++++++ Expected Exception - Print Company that's not exists(1) ++++++++++");
        catchAndCheckException(CouponSystemException.class, () -> System.out.println(adminService.getOneCompany(1)));


        // Print all Companies
        System.out.println();
        System.out.println(adminService.getAllCompanies());


        // Add random x20 Customers
        System.out.println();
        System.out.println("++++++++++ Expected Exception - Add random x20 Customers ++++++++++");
        for (int i = 1; i <= 20; i++) {
            Customer customer = new Customer();
            customer.setFirstName("FirstName " + i);
            customer.setLastName("LastName " + i);
            customer.setEmail("customer" + i + "@test.com");
            customer.setPassword("password" + i);
            catchAndCheckException(CouponSystemException.class, () -> adminService.addCustomer(customer));
        }


        // Update Customer
        System.out.println();
        Customer customer = new Customer();
        customer.setFirstName("updatedCustomer");
        customer.setLastName("updatedCustomer");
        customer.setEmail("updatedCustomer" + "@test.com");
        customer.setPassword("updatedCustomer123");
        catchAndCheckException(CouponSystemException.class, () -> adminService.updateCustomer(customer));

        // Update Customer with exists by Email
        System.out.println();
        System.out.println("++++++++++ Expected Exception - Update Customer with exists by Email ++++++++++");
        Customer customer1 = new Customer();
        customer1.setFirstName("updatedCustomer");
        customer1.setLastName("updatedCustomer");
        customer1.setEmail("updatedCustomer" + "@test.com");
        customer1.setPassword("updatedCustomer123");
        catchAndCheckException(CouponSystemException.class, () -> adminService.updateCustomer(customer1));


        // Delete Customer
        System.out.println();
        catchAndCheckException(CouponSystemException.class, () -> adminService.deleteCustomer(5));


        // Try to Delete Customer that's not exists
        System.out.println();
        System.out.println("++++++++++ Expected Exception - Try to Delete Customer that's not exists ++++++++++");
        catchAndCheckException(CouponSystemException.class, () -> adminService.deleteCustomer(5));


        //Print all Customers
        System.out.println();
        adminService.getAllCustomers();

        //Print Customer(2)
        System.out.println();


        catchAndCheckException(CouponSystemException.class, () -> System.out.println(adminService.getOneCustomer(2)));


        //Print Customer that's not exists(1)
        System.out.println();
        System.out.println("++++++++++ Expected Exception - Print Customer that's not exists(1) ++++++++++");
        catchAndCheckException(CouponSystemException.class, () -> System.out.println(adminService.getOneCustomer(1)));

    }


    @Test
    @Order(3)
    public void companyTest() throws Throwable {
        System.out.println("============ Company Test ============");
        CompanyService companyService = (CompanyService) loginManager.login("company@company.com", "company", ClientType.Company);

        Company company = companyService.getCompanyByEmail("company@company.com");
        int loginCompanyId = company.getId();

        //Add random x20 Coupons
        System.out.println();
        System.out.println("++++++++++ Expected Exception - Add random x20 Coupons ++++++++++");
        Random rand = new Random();
        for (int i = 1; i <= 20; i++) {
            Coupon coupon = new Coupon();
            coupon.setCompany(rand.nextInt(20));
            coupon.setCategory(Category.values()[rand.nextInt(Category.values().length)]);
            coupon.setTitle("Coupon " + i);
            coupon.setDescription("Description " + i);
            coupon.setStartDate(LocalDate.now());
            int randomValue = rand.nextInt(5) - 2;
            coupon.setEndDate(LocalDate.now().plusMonths(randomValue));
            coupon.setAmount(rand.nextInt(100));
            coupon.setPrice(rand.nextDouble() * 100);
            coupon.setImage("image" + i + ".jpg");

            catchAndCheckException(CouponSystemException.class, () -> companyService.addCoupon(coupon));
        }

        // Get one Coupon(5)
        System.out.println();
        catchAndCheckException(CouponSystemException.class, () ->  companyService.getOneCoupon(5));

        // Update Coupon(1)
        System.out.println();
        Coupon updateCoupon = new Coupon();
        updateCoupon.setTitle("Coupon 1");
        updateCoupon.setDescription("UpdatedCoupon");
        updateCoupon.setStartDate(LocalDate.now());
        updateCoupon.setEndDate(LocalDate.now().minusMonths(1));
        updateCoupon.setAmount(rand.nextInt(10));
        catchAndCheckException(CouponSystemException.class, () -> companyService.updateCoupon(updateCoupon));

        // Delete Coupon(2)
        System.out.println();
        catchAndCheckException(CouponSystemException.class, () -> companyService.deleteCoupon(2));

        // Trying to delete the same coupon again(2)
        System.out.println();
        System.out.println("++++++++++ Expected Exception - Trying to delete the same coupon again(2) ++++++++++");
        catchAndCheckException(CouponSystemException.class, () -> companyService.deleteCoupon(2));

        // Get Company all coupons(1)
        System.out.println();
        catchAndCheckException(CouponSystemException.class, () -> companyService.getCompanyAllCoupons(loginCompanyId));

        System.out.println();
        catchAndCheckException(CouponSystemException.class, () -> companyService.getCouponsByCompanyIdAndCategory(loginCompanyId, Category.Food));

        System.out.println();
        catchAndCheckException(CouponSystemException.class, () -> companyService.findByCompanyIdAndPriceLessThanEqual(loginCompanyId, 50));

        System.out.println();
        catchAndCheckException(CouponSystemException.class, () -> companyService.getOneCompany(loginCompanyId));


    }


    @Test
    @Order(4)
    public void customerTest() throws Throwable {
        System.out.println("============ Customer Test ============");
        CustomerService customerService = (CustomerService) loginManager.login("Testcustomer@customer.com", "customer", ClientType.Customer);

        Customer customer = customerService.getCustomerByEmail("Testcustomer@customer.com");
        int loginCustomerId = customer.getId();


        System.out.println();
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            int couponId = rand.nextInt(15);
            catchAndCheckException(CouponSystemException.class, () ->
                    customerService.purchaseCoupon(loginCustomerId, couponId));
        }

        System.out.println();
        catchAndCheckException(CouponSystemException.class, () ->
                System.out.println(customerService.getCustomerCoupons(loginCustomerId)));

        // Get all coupons By customerId(LoggedIn) and By Category(Random)
        System.out.println();
        String categoryRand = String.valueOf(Category.values()[rand.nextInt(Category.values().length)]);
        catchAndCheckException(CouponSystemException.class, () -> {
            customerService.getCouponsByCategoryAndCustomerId(loginCustomerId, categoryRand);
        });


        System.out.println();
        catchAndCheckException(CouponSystemException.class, ()
                -> customerService.getCustomerIdAndPriceLessThanEqual(loginCustomerId, 50));

        System.out.println();
        catchAndCheckException(CouponSystemException.class, ()
                -> customerService.getOneCustomer(loginCustomerId));


    }

    public <T extends Throwable> T catchAndCheckException(Class<T> type, Executable executable) throws Throwable {
        try {
            executable.execute();
        } catch (Throwable e) {
            if (type.isInstance(e)) {
                T exception = type.cast(e);
                System.out.println(exception.getMessage());
                return exception;
            } else {
                throw e;
            }
        }
        return null;
    }

}


