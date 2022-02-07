package team.project.yumarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.project.yumarket.network.dto.request.homeItem.HomeItemLikeRequestDto;
import team.project.yumarket.network.dto.response.homeItem.HomeItemLikeResponseDto;
import team.project.yumarket.service.HomeItemLikeApiService;
import team.project.yumarket.util.url.Urls;

import javax.annotation.PostConstruct;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/04
 */
@RestController
@RequestMapping(Urls.HOME_ITEM_LIKE)
@RequiredArgsConstructor
public class HomeItemLikeApiController extends BaseController<HomeItemLikeRequestDto, HomeItemLikeResponseDto> {

    private final HomeItemLikeApiService homeItemLikeApiService;

    @PostConstruct
    public void init() {
        this.baseService = homeItemLikeApiService;
    }
}
