package team.project.yumarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.project.yumarket.network.dto.request.MarketLikeRequestDto;
import team.project.yumarket.network.dto.response.MarketLikeResponseDto;
import team.project.yumarket.service.MarketLikeApiService;
import team.project.yumarket.util.url.Urls;

import javax.annotation.PostConstruct;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/22
 */
@RestController
@RequestMapping(Urls.MARKET_LIKE) // http://localhost:8080/api/town-market/like
@RequiredArgsConstructor
public class MarketLikeApiController extends BaseController<MarketLikeRequestDto, MarketLikeResponseDto> {

    private final MarketLikeApiService marketLikeApiService;

    @PostConstruct
    public void init() {
        this.baseService = marketLikeApiService;
    }
}
