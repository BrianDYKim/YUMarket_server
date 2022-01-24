package team.project.yumarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.project.yumarket.network.dto.request.MarketReviewRequestDto;
import team.project.yumarket.network.dto.response.MarketReviewResponseDto;
import team.project.yumarket.service.MarketReviewApiService;
import team.project.yumarket.util.url.Urls;

import javax.annotation.PostConstruct;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(Urls.MARKET_REVIEW)
public class MarketReviewApiController extends BaseController<MarketReviewRequestDto, MarketReviewResponseDto> {

    private final MarketReviewApiService marketReviewApiService;

    @PostConstruct
    public void init() {
        this.baseService = marketReviewApiService;
    }
}
