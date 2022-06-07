package com.remember.core.AuthApp.exceptions;

public class OAuthProviderMissMatchException extends RuntimeException {
    public OAuthProviderMissMatchException(String message) {
        super(message);
    }
}
