package team.project.yumarket.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용자의 권한을 정의하는 enum class
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/19
 */
@Getter
@RequiredArgsConstructor
public enum Role {

    ROLE_USER(0, "ROLE_USER", "일반 사용자"),
    ROLE_GUEST(1, "ROLE_GUEST", "손님");

    private final Integer id;

    private final String key;

    private final String title;
}
