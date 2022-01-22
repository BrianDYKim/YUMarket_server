package team.project.yumarket.model.enums.home;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/19
 */
@Getter
@RequiredArgsConstructor
public enum DetailCategory {

    ALL("전체"),
    EXTRA("기타"),
    JOKBAL_BOSSAM("족발/보쌈"),
    CUTLET_JAPAN("돈가스.일식"),
    CAFE_BREAD("카페/빵집"),
    DESSERT("디저트"),
    FAST_FOOD("패스트푸드"),
    CHINA_FOOD("중국집"),
    PIZZA("피자"),
    SNACK_BREAD("과자/빵"),
    BEVERAGE_COFFEE_MILK_PRODUCT("음료/커피/유제품"),
    INSTANT_COOK("즉석조리식품"),
    WASHING_PRODUCT("세탁용품"),
    HAIR_SHOP("헤어샵/미용실"),
    MASSAGE_AND_SKIN_CARE("마사지/피부관리"),
    STUDY_CAFE("스터디카페"),
    MAN_TOP("남성상의"),
    MAN_PANTS("남성하의"),
    WOMAN_TOP("여성상의"),
    WOMAN_PANTS("여성하의"),
    SHOES("신발"),
    MAN_BAG("남성가방"),
    WOMAN_BAG("여성가방"),
    EARRINGS("귀걸이"),
    ETC_ALL("전체");

    private final String title;
}
