package org.amos.core.basic.exception;

public class LimiterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public LimiterException(String message){
        super(message);
    }

    public LimiterException(Throwable cause)
    {
        super(cause);
    }

    public LimiterException(String message, Throwable cause)
    {
        super(message,cause);
    }
}
