package com.best.practice.BestPractice.exception;

public class ResourceExist extends RuntimeException {
    public ResourceExist(String msg) {
        super(msg);
    }
}
