package org.example;

public class InsufficientFundsException extends Exception{
    public InsufficientFundsException(String exception){
        super(exception);
    }
}
