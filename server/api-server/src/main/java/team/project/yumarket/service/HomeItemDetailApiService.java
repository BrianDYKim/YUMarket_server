package team.project.yumarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.project.yumarket.model.entity.home.HomeItem;
import team.project.yumarket.network.dto.response.homeItem.HomeItemDetailResponseDto;
import team.project.yumarket.network.dto.response.homeItem.HomeItemResponseDto;
import team.project.yumarket.network.dto.response.townMarket.TownMarketSimpleResponseDto;
import team.project.yumarket.network.exception.EntityNotFoundException;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.network.formats.Pagination;
import team.project.yumarket.repository.HomeItemRepository;
import team.project.yumarket.util.url.Urls;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * HomeItem에 대한 Detail read 기능만을 지원하는 service logic code
 *
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/07
 */
@Service
@RequiredArgsConstructor
public class HomeItemDetailApiService {

    private final String REQUEST_URL = Urls.BASE_URL + Urls.HOME_ITEM_DETAIL; // /api/item-detail

    // injections
    public final HomeItemRepository homeItemRepository;

    // detail read
    public ResponseEntity<CommunicationFormat<HomeItemDetailResponseDto>> read(HomeItem homeItem, TownMarketSimpleResponseDto marketDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(response(REQUEST_URL + "/" + homeItem.getId(), homeItem, marketDto));
    }

    // detail search
    public ResponseEntity<CommunicationFormat<List<HomeItemDetailResponseDto>>> search(
            Page<HomeItem> items, List<HomeItemDetailResponseDto> itemDetailResponseDtoList) {

        Pagination pagination = Pagination.builder()
                .totalPage(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .currentPage(items.getNumber())
                .currentElements(items.getNumberOfElements())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommunicationFormat.OK(REQUEST_URL + "/list?category=xx" + "&page=" + pagination.getCurrentPage(),
                        itemDetailResponseDtoList, pagination)
                );
    }

    public HomeItemDetailResponseDto responseData(HomeItem homeItem, TownMarketSimpleResponseDto marketDto) {
        return HomeItemDetailResponseDto.builder()
                .id(homeItem.getId())
                .category(homeItem.getCategory())
                .detailCategory(homeItem.getDetailCategory())
                .itemImageUrl(homeItem.getItemImageUrl())
                .name(homeItem.getName())
                .originalPrice(homeItem.getOriginalPrice())
                .salePrice(homeItem.getSalePrice())
                .stockQuantity(homeItem.getStockQuantity())
                .createdAt(homeItem.getCreatedAt())
                .saleUpdatedAt(homeItem.getSaleUpdatedAt())
                .likeQuantity(homeItem.getHomeItemLikeList().size())
                .reviewQuantity(homeItem.getHomeItemReviewList().size())
                .townMarketId(homeItem.getTownMarket().getId())
                .marketInfo(marketDto)
                .build();
    }

    private CommunicationFormat<HomeItemDetailResponseDto> response(String url, HomeItem homeItem, TownMarketSimpleResponseDto marketDto) {
        return CommunicationFormat.OK(url, responseData(homeItem, marketDto));
    }
}
