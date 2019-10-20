package com.target.myretail.exception;

public class ProductNotFoundException extends RuntimeException {

    private String errorCode;

    public ProductNotFoundException(long productId){
        super(String.format("Product not found: %s", productId));
        errorCode = "MRS-0001";
    }

    public String getErrorCode ()
    {
        return errorCode;
    }

    public void setErrorCode (String errorCode)
    {
        this.errorCode = errorCode;
    }
}
