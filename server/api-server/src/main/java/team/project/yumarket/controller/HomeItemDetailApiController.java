package team.project.yumarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.project.yumarket.model.entity.home.HomeItem;
import team.project.yumarket.model.entity.home.TownMarket;
import team.project.yumarket.model.enums.home.Category;
import team.project.yumarket.network.dto.response.homeItem.HomeItemDetailResponseDto;
import team.project.yumarket.network.dto.response.townMarket.TownMarketSimpleResponseDto;
import team.project.yumarket.network.exception.EntityNotFoundException;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.service.HomeItemDetailApiService;
import team.project.yumarket.service.TownMarketApiService;
import team.project.yumarket.util.url.Urls;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/07
 */
@RestController
@RequestMapping(Urls.HOME_ITEM_DETAIL)
@RequiredArgsConstructor
public class HomeItemDetailApiController {

    // Dependency injection
    private final HomeItemDetailApiService homeItemDetailApiService;
    private final TownMarketApiService townMarketApiService;

    // detail read method
    @GetMapping("/{id}")
    public ResponseEntity<CommunicationFormat<HomeItemDetailResponseDto>> read(@PathVariable Long id) {
        return homeItemDetailApiService.homeItemRepository.findById(id).map(homeItem ->
                homeItemDetailApiService.read(homeItem, getTownMarketSimpleResponseDto(homeItem))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    // detail search method by category
    @GetMapping("/list")
    public ResponseEntity<CommunicationFormat<List<HomeItemDetailResponseDto>>> searchByCategory(Pageable pageable, @RequestParam Category category) {
        Page<HomeItem> items = homeItemDetailApiService.homeItemRepository.findAllByCategory(pageable, category);

        List<HomeItemDetailResponseDto> itemDetailResponseDtoList = getDetailResponseDtoList(items);

        return homeItemDetailApiService.search(items, itemDetailResponseDtoList);
    }

    // homeItem를 통해서 townMarket의 정보를 뽑아와주는 메소드
    private TownMarketSimpleResponseDto getTownMarketSimpleResponseDto(HomeItem homeItem) {
        Long marketId = homeItem.getTownMarket().getId();
        TownMarket market = townMarketApiService.townMarketRepository.getById(marketId);

        return townMarketApiService.responseSimpleData(market);
    }

    // item에 관한 page를 통해 itemDetailResponseDtoList를 추출해내는 메소드
    private List<HomeItemDetailResponseDto> getDetailResponseDtoList(Page<HomeItem> items) {
        return items.stream().map(homeItem ->
                homeItemDetailApiService.responseData(homeItem, getTownMarketSimpleResponseDto(homeItem))
        ).collect(Collectors.toList());
    }
}
