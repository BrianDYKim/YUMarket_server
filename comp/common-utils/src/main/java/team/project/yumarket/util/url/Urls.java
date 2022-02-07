package team.project.yumarket.util.url;

/**
 * 본 서버의 request Url들을 모아둔 interface
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/21
 */
public interface Urls {

    String BASE_URL = "http://localhost:9000"; // base url

    String TOWN_MARKET = "/api/town-market"; // /api/town-market

    String MARKET_LIKE = TOWN_MARKET + "-like"; // /api/town-market-like

    String MARKET_REVIEW = TOWN_MARKET + "-review"; // /api/town-market-review

    String HOME_ITEM = "/api/item"; // /api/item

    String HOME_ITEM_LIKE = HOME_ITEM + "-like"; // /api/item-like

    String HOME_ITEM_REVIEW = HOME_ITEM + "-review"; // /api/item-review

    String HOME_ITEM_DETAIL = HOME_ITEM + "-detail"; // /api/item-detail
}
