package com.crio.coderHack.exceptions;

import java.io.IOException;

public class UserNotFoundException extends IOException {
    
    public UserNotFoundException() {

    }

    public UserNotFoundException(String message) {
        super(message);
    }
}