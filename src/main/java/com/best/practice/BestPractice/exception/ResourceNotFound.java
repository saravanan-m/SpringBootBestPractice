package com.best.practice.BestPractice.exception;

public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(String msg) {
        super(msg);
    }
}
