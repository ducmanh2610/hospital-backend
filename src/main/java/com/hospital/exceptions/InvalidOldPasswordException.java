package com.hospital.exceptions;

public class InvalidOldPasswordException extends RuntimeException{
    public InvalidOldPasswordException() {
        super();
    }
    public InvalidOldPasswordException(final String message, final Throwable cause) {
        super(message, cause);
    }
    public InvalidOldPasswordException(final Throwable cause) {
        super(cause);
    }
}
