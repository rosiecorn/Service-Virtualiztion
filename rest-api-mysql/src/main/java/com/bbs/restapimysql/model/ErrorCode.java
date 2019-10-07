package com.bbs.restapimysql.model;

public class ErrorCode {


    private String code ;
    private String message;


    public ErrorCode( String responsecode, String responsemessage)

    {
        this.code = responsecode;
        this.message = responsemessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
