package team.project.yumarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.project.yumarket.network.dto.request.MarketReviewRequestDto;
import team.project.yumarket.network.dto.response.MarketReviewResponseDto;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.service.MarketReviewApiService;
import team.project.yumarket.util.url.Urls;

import javax.annotation.PostConstruct;
import java.util.List;

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

    // url : /api/town-market-review/list?market
    // townMarketId를 이용하여 동네마켓의 모든 리뷰 리스트를 뽑아오는 메소드
    @GetMapping("/lists")
    public ResponseEntity<CommunicationFormat<List<MarketReviewResponseDto>>> readAllByTownMarketId(
            @RequestParam(name = "market-id") Long marketId) {
        return marketReviewApiService.readReviewsByMarketId(marketId);
    }
}
