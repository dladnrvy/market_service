package com.example.market_service.exception;

public class NotFonudTokenException extends RuntimeException{
    public NotFonudTokenException() {
        super();
    }

    public NotFonudTokenException(String message) {
        super(message);
    }

    public NotFonudTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFonudTokenException(Throwable cause) {
        super(cause);
    }

    protected NotFonudTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
