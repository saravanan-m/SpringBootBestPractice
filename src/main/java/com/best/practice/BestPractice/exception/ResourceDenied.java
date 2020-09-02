package com.best.practice.BestPractice.exception;

public class ResourceDenied extends RuntimeException {
    public ResourceDenied(String msg) {
        super(msg);
    }
}
