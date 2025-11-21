package com.company.jobank.exceptions;

public class InvalidTransferException extends  RuntimeException {
    public InvalidTransferException(String message) {
        super(message);
    }
}
