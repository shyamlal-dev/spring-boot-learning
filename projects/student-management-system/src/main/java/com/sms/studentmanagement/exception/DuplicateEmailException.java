package com.sms.studentmanagement.exception;

public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String message){
        super(message);
    } 
}