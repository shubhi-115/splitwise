package com.example.Splitwise.exception;

public class GroupNotFoundException extends Exception{
    public GroupNotFoundException(String message){
        super(message);
    }
    public GroupNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
