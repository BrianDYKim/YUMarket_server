package team.project.yumarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.project.yumarket.network.dto.request.townMarket.TownMarketRequestDto;
import team.project.yumarket.network.dto.response.homeItem.HomeItemResponseDto;
import team.project.yumarket.network.dto.response.townMarket.MarketReviewResponseDto;
import team.project.yumarket.network.dto.response.townMarket.TownMarketDetailResponseDto;
import team.project.yumarket.network.dto.response.townMarket.TownMarketResponseDto;
import team.project.yumarket.network.exception.EntityNotFoundException;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.service.HomeItemApiService;
import team.project.yumarket.service.MarketReviewApiService;
import team.project.yumarket.service.TownMarketApiService;
import team.project.yumarket.util.url.Urls;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/20
 */
@RestController
@RequestMapping(Urls.TOWN_MARKET)
@RequiredArgsConstructor
public class TownMarketApiController extends BaseController<TownMarketRequestDto, TownMarketResponseDto> {

    private final TownMarketApiService townMarketApiService;

    // controller layer에서 여러 service를 참조하기
    private final MarketReviewApiService marketReviewApiService;
    private final HomeItemApiService homeItemApiService;

    @PostConstruct
    public void init() {
        this.baseService = townMarketApiService;
    }

    // 동네마켓에 대한 detail response를 날려주는 GET Method
    @GetMapping("/detail/{id}")
    public ResponseEntity<CommunicationFormat<TownMarketDetailResponseDto>> detailRead(@PathVariable Long id) {

        return townMarketApiService.townMarketRepository.findById(id).map(townMarket -> {
                    List<MarketReviewResponseDto> marketReviewResponseDtoList
                            = marketReviewApiService.responseDataList(townMarket.getMarketReviewList());
                    List<HomeItemResponseDto> homeItemResponseDtoList
                            = homeItemApiService.responseDataList(townMarket.getHomeItemList());

                    return townMarketApiService.detailRead(
                            id, marketReviewResponseDtoList, homeItemResponseDtoList
                    );
        }
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }
}
