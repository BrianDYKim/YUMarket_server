package team.project.yumarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.project.yumarket.network.dto.request.HomeItemRequestDto;
import team.project.yumarket.network.dto.response.HomeItemResponseDto;
import team.project.yumarket.service.HomeItemApiService;
import team.project.yumarket.util.url.Urls;

import javax.annotation.PostConstruct;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/20
 */

@RestController
@RequestMapping(Urls.HOME_ITEM) // /api/item
@RequiredArgsConstructor
public class HomeItemApiController extends BaseController<HomeItemRequestDto, HomeItemResponseDto> {

    private final HomeItemApiService homeItemApiService;

    @PostConstruct
    public void init() {
        this.baseService = homeItemApiService;
    }
}
