package team.project.yumarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.project.yumarket.service.MarketReviewApiService;
import team.project.yumarket.util.url.Urls;

import javax.annotation.PostConstruct;

/**
 * 동네마켓에 대한 리뷰를 관리하는 controller class /
 * request url : /api/town-market/review
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/23
 */
@RestController
@RequestMapping(Urls.MARKET_REVIEW)
@RequiredArgsConstructor
public class MarketReviewApiController extends BaseController {

    private final MarketReviewApiService marketReviewApiService;

    @PostConstruct
    public void init() {
        this.baseService = marketReviewApiService;
    }
}
