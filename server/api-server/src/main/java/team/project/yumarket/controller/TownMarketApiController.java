package team.project.yumarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.project.yumarket.network.dto.request.TownMarketRequestDto;
import team.project.yumarket.network.dto.response.TownMarketResponseDto;
import team.project.yumarket.service.TownMarketApiService;
import team.project.yumarket.util.url.Urls;

import javax.annotation.PostConstruct;

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

    @PostConstruct
    public void init() {
        this.baseService = townMarketApiService;
    }
}
