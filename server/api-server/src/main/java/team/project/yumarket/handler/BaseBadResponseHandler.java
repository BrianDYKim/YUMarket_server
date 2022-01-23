package team.project.yumarket.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import team.project.yumarket.network.formats.ErrorResponseFormat;
import team.project.yumarket.util.codes.ErrorCode;

/**
 * Exception Handler 모두가 상속을 받아야하는 클래스
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/23
 */
public class BaseBadResponseHandler {

    // valid에서 걸리는 exception을 모두 처리하는 메소드
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseFormat> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseFormat.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult())
                );
    }
}
