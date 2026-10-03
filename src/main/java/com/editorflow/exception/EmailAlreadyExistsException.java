package com.editorflow.exception;

import com.editorflow.dto.common.ErrorResponse;

//package com.editorflow.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("An account with email '" + email + "' already exists.");
    }
}
