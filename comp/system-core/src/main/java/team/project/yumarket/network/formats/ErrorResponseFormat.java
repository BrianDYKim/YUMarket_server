package team.project.yumarket.network.formats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import team.project.yumarket.network.errorCode.ErrorCode;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/23
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponseFormat {

    private String message;

    private int status; // 해당 에러의 HTTP Error code를 저장하는 변수

    @JsonProperty("error_list")
    private List<FieldError> errorList;

    private String code;

    // Constructor
    private ErrorResponseFormat(ErrorCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }

    private ErrorResponseFormat(ErrorCode code, List<FieldError> errorList) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errorList = errorList;
    }

    // field에 대한 error가 존재하지 않는 경우 호출하는 메소드
    public static ErrorResponseFormat of(ErrorCode code) {
        return new ErrorResponseFormat(code);
    }

    // errorList를 직접 아는 경우 호출하는 메소드
    public static ErrorResponseFormat of(ErrorCode code, List<FieldError> errorList) {
        return new ErrorResponseFormat(code, errorList);
    }

    // bindingResult를 파라미터로 보내서 에러를 처리하는 메소드
    public static ErrorResponseFormat of(ErrorCode code, BindingResult bindingResult) {
        return new ErrorResponseFormat(code, FieldError.of(bindingResult));
    }

    // ConstraintViolationException에 대해서 에러를 처리하는 메소드
    public static ErrorResponseFormat of(ConstraintViolationException e) {
        List<FieldError> errorList = e.getConstraintViolations().stream().map(error -> {

            String field = error.getPropertyPath().toString();
            String message = error.getMessage();
            String invalidValue = error.getInvalidValue().toString();

            return FieldError.of(field, invalidValue, message).get(0);
        }).collect(Collectors.toList());

        return new ErrorResponseFormat(ErrorCode.INVALID_INPUT_VALUE, errorList);
    }

    // MethodArgumentTypeMisMatchException에 대해서 에러를 처리하는 메소드
    public static ErrorResponseFormat of(MethodArgumentTypeMismatchException e) {
        String value = e.getValue() == null? "" : e.getValue().toString();
        List<FieldError> errorList = FieldError.of(e.getName(), value, e.getErrorCode());

        return new ErrorResponseFormat(ErrorCode.INVALID_INPUT_VALUE, errorList);
    }

    @Getter
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        // 단일 error
        public static List<FieldError> of(String field, String value, String reason) {
            List<FieldError> errorList = new ArrayList<>();
            errorList.add(new FieldError(field, value, reason));

            return errorList;
        }

        // 복수의 에러
        public static List<FieldError> of(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream().map(error -> new FieldError(
                    error.getField(), error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                    error.getDefaultMessage())
            ).collect(Collectors.toList());
        }
    }
}