package com.example.demo;

public class CustomerRepo {
    private String message;
    private String responseTime;
    private String transactionid;

    public CustomerRepo(String message, String responseTime, String transactionid) {
        super();
        this.message = message;
        this.responseTime = responseTime;
        this.transactionid = transactionid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }
}
