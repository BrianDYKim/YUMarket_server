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
    // Common(40x)
    INVALID_INPUT_VALUE(400, "C001", "Invalid input value"),
    ACCESS_DENIED(400, "C002", "Access is denied"),

    // Common(50x)
    ENTITY_NOT_FOUND(500, "C101", "Entity is not found");

    private int status;

    private String code;

    private String message;
}
