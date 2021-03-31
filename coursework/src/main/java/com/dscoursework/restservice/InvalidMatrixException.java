package com.dscoursework.restservice;

public class InvalidMatrixException extends Exception {
    public InvalidMatrixException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
