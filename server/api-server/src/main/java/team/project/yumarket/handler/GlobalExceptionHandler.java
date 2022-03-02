package team.project.yumarket.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import team.project.yumarket.network.exception.EntityNotFoundException;
import team.project.yumarket.network.formats.ErrorResponseFormat;
import team.project.yumarket.network.errorCode.ErrorCode;

import javax.validation.ConstraintViolationException;

/** Global Exception Handler class
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/23
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // requestBody validation에서 걸리는 exception을 모두 처리하는 메소드
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseFormat> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseFormat.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult())
                );
    }

    // Method의 파라미터나 리턴 값에 validation이 걸리는 경우를 처리하는 메소드
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponseFormat> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseFormat.of(e)
                );
    }

    // Enum binding이 실패한 경우에는 이 메소드를 통해서 예외 처리를 시행한다.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponseFormat> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseFormat.of(e)
                );
    }

    // 지원하지 않는 HTTP 요청을 하는 경우 예외 처리를 시행하는 메소드
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponseFormat> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseFormat.of(ErrorCode.ACCESS_DENIED)
                );
    }

    // Db 상에 존재하지 않는 엔티티에 접근을 시도할 때 발생하는 Exception을 처리하는 메소드
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponseFormat> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseFormat.of(e.getErrorCode())
                );
    }
}
