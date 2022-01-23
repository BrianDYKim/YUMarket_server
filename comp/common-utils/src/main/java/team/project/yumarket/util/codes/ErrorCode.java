package team.project.yumarket.util.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드를 관리하는 enum class
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/23
 */

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "Invalid input value");

    private int status;

    private String code;

    private String message;
}
