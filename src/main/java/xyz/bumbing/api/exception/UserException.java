package xyz.bumbing.api.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    protected ErrorCode errorCode;

    public UserException() {
        super();
    }

    public UserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public UserException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public UserException(ErrorCode errorCode, String msg, Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

    public UserException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserException(String msg) {
        super(msg);
    }


}
