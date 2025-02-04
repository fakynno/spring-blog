package com.fiap.springblog.exceptions;

public class OptimisticLockingFailureException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OptimisticLockingFailureException(String message) {
        super(message);
    }

}
