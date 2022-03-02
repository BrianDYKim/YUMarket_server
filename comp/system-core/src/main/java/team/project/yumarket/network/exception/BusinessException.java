package team.project.yumarket.network.exception;

import team.project.yumarket.network.errorCode.ErrorCode;

/**
 * Business Logic 상의 Exception을 처리하는 Exception class
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/24
 */
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
