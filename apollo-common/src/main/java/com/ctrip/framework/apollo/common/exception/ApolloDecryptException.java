package com.ctrip.framework.apollo.common.exception;

public class ApolloDecryptException extends RuntimeException {
    public ApolloDecryptException(String message) {
        super(message);
    }

    public ApolloDecryptException(String message, Throwable cause) {
        super(message, cause);
    }
}
