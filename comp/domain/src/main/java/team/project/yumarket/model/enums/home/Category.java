package team.project.yumarket.model.enums.home;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * HomeItem의 대분류를 저장하는 enum class
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/07
 */
@Getter
@RequiredArgsConstructor
public enum Category {
    FOOD_BEVERAGE("식/음료"),
    MART("편의점/마트"),
    SERVICE("서비스업종"),
    FASHION("패션의류"),
    ACCESSORY("패션잡화"),
    ETC("그외/마켓");

    private final String title;
}
