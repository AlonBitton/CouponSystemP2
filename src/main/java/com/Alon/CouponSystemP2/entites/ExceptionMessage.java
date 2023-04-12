package com.Alon.CouponSystemP2.entites;

public enum ExceptionMessage {
    VALUE_CANNOT_BE_CHANGED("This value cannot be changed"),
    EMAIL_ALREADY_EXIST("This email is already taken, try different email"),
    EXIST_BY_NAME("This name is already taken, try different name"),
    TITLE_ALREADY_EXIST("This coupon title is already taken, try different title"),
    COMPANY_NOT_EXIST("Company doesn't exist"),
    CUSTOMER_NOT_EXIST("Customer doesn't exist"),
    COUPON_NOT_EXIST("Coupon doesn't exist"),
    COUPON_AMOUNT_IS_ZERO("Coupon's amount is 0, cannot be purchased"),
    COUPON_EXPIRED("Coupon is expired, cannot be purchased"),
    COUPON_ALREADY_PURCHASED("Coupon was already purchased"),
    AUTHENTICATION_FAILED("Your email or password is wrong."),
    GENERAL_ERROR("Operation Failed.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}