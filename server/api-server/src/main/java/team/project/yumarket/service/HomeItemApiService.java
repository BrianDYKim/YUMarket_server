package team.project.yumarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import team.project.yumarket.ifs.ServiceCrudInterface;
import team.project.yumarket.model.entity.home.HomeItem;
import team.project.yumarket.network.dto.request.homeItem.HomeItemRequestDto;
import team.project.yumarket.network.dto.response.homeItem.HomeItemResponseDto;
import team.project.yumarket.network.exception.EntityNotFoundException;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.repository.HomeItemRepository;
import team.project.yumarket.repository.TownMarketRepository;
import team.project.yumarket.util.url.Urls;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/20
 */
@Service
@RequiredArgsConstructor
public class HomeItemApiService implements ServiceCrudInterface<HomeItem, HomeItemRequestDto, HomeItemResponseDto> {

    private final String REQUEST_URL = Urls.BASE_URL + Urls.HOME_ITEM;

    // dependency injection
    private final HomeItemRepository homeItemRepository;
    private final TownMarketRepository townMarketRepository;

    // 새로운 아이템을 생성하는 메소드
    @Override
    public ResponseEntity<CommunicationFormat> create(CommunicationFormat<HomeItemRequestDto> request) {
        // 예외 검출
        if (!townMarketRepository.existsById(request.getData().getTownMarketId()))
            throw new EntityNotFoundException("Entity is not found");

        HomeItemRequestDto requestBody = request.getData();
        HomeItem homeItem = requestToEntity(requestBody);

        homeItemRepository.save(homeItem);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommunicationFormat.OK(REQUEST_URL)
                );
    }

    // 새로운 아이템을 읽어내는 메소드
    @Override
    public ResponseEntity<CommunicationFormat<HomeItemResponseDto>> read(Long id) {
        return homeItemRepository.findById(id).map(homeItem ->
                ResponseEntity.status(HttpStatus.OK)
                        .body(response(REQUEST_URL + "/" + id, homeItem))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    // 기존의 아이템을 수정하는 메소드
    @Override
    public ResponseEntity<CommunicationFormat<HomeItemResponseDto>> update(CommunicationFormat<HomeItemRequestDto> request, Long id) throws HttpRequestMethodNotSupportedException {
        HomeItemRequestDto requestBody = request.getData();

        return homeItemRepository.findById(id).map(homeItem -> {
                    homeItem.setName(requestBody.getName() == null ? homeItem.getName() : requestBody.getName())
                            .setOriginalPrice(requestBody.getOriginalPrice() == null ? homeItem.getOriginalPrice() : requestBody.getOriginalPrice())
                            .setSalePrice(requestBody.getSalePrice() == null ? homeItem.getSalePrice() : requestBody.getSalePrice())
                            .setItemImageUrl(requestBody.getItemImageUrl() == null ? homeItem.getItemImageUrl() : requestBody.getItemImageUrl())
                            .setCategory(requestBody.getCategory() == null? homeItem.getCategory() : requestBody.getCategory())
                            .setDetailCategory(requestBody.getDetailCategory() == null ? homeItem.getDetailCategory() : requestBody.getDetailCategory())
                            .setSaleUpdatedAt(requestBody.getSalePrice() == null ? homeItem.getSaleUpdatedAt() : LocalDateTime.now())
                            .setStockQuantity(requestBody.getStockQuantity() == null ? homeItem.getStockQuantity() : requestBody.getStockQuantity());

                    return homeItem;
                }
        ).map(homeItem -> homeItemRepository.save(homeItem)
        ).map(homeItem -> ResponseEntity.status(HttpStatus.OK)
                .body(response(REQUEST_URL + "/" + id, homeItem))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat> delete(Long id) {
        return homeItemRepository.findById(id).map(homeItem -> {
            homeItemRepository.delete(homeItem);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(CommunicationFormat.OK(REQUEST_URL + "/" + id)
                    );
        }).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<List<HomeItemResponseDto>>> search(Pageable pageable) throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException("Access is denied");
    }

    // TODO 동네마켓 id을 통해서 해당 동네마켓의 아이템들을 모두 반환하는 메소드 작성


    @Override
    public CommunicationFormat<HomeItemResponseDto> response(String url, HomeItem homeItem) {
        return CommunicationFormat.OK(url, responseData(homeItem));
    }

    @Override
    public HomeItemResponseDto responseData(HomeItem homeItem) {
        return HomeItemResponseDto.builder()
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
                .build();
    }

    // homeItem의 List를 받아와서 ResponseDto의 List로 반환해주는 메소드
    public List<HomeItemResponseDto> responseDataList(List<HomeItem> homeItemList) {
        return homeItemList.stream().map(homeItem -> responseData(homeItem)
        ).collect(Collectors.toList());
    }

    @Override
    public HomeItem requestToEntity(HomeItemRequestDto request) {
        return HomeItem.builder()
                .category(request.getCategory())
                .detailCategory(request.getDetailCategory())
                .itemImageUrl(request.getItemImageUrl())
                .name(request.getName())
                .originalPrice(request.getOriginalPrice())
                .salePrice(request.getSalePrice())
                .stockQuantity(request.getStockQuantity())
                .townMarket(townMarketRepository.getById(request.getTownMarketId()))
                .build();
    }
}
