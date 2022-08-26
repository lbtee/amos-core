package org.amos.core.basic.exception;

import lombok.Getter;

@Getter
public class GenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GenException(String message) {
        super(message);
    }

    public GenException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenException(Throwable cause) {
        super(cause);
    }

}
