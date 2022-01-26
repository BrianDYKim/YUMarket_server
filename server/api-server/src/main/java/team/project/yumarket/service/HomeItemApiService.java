package team.project.yumarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import team.project.yumarket.ifs.ServiceCrudInterface;
import team.project.yumarket.model.entity.home.HomeItem;
import team.project.yumarket.network.dto.request.HomeItemRequestDto;
import team.project.yumarket.network.dto.response.HomeItemResponseDto;
import team.project.yumarket.network.formats.CommunicationFormat;

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

    @Override
    public ResponseEntity<CommunicationFormat> create(CommunicationFormat<HomeItemRequestDto> request) {
        return null;
    }

    @Override
    public ResponseEntity<CommunicationFormat<HomeItemResponseDto>> read(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommunicationFormat<HomeItemResponseDto>> update(CommunicationFormat<HomeItemRequestDto> request, Long id) throws HttpRequestMethodNotSupportedException {
        return null;
    }

    @Override
    public ResponseEntity<CommunicationFormat> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommunicationFormat<List<HomeItemResponseDto>>> search(Pageable pageable) throws HttpRequestMethodNotSupportedException {
        return null;
    }

    @Override
    public CommunicationFormat<HomeItemResponseDto> response(String url, HomeItem homeItem) {
        return CommunicationFormat.OK(url, responseData(homeItem));
    }

    @Override
    public HomeItemResponseDto responseData(HomeItem homeItem) {
        return HomeItemResponseDto.builder()
                .id(homeItem.getId())
                .detailCategory(homeItem.getDetailCategory())
                .itemImageUrl(homeItem.getItemImageUrl())
                .name(homeItem.getName())
                .originalPrice(homeItem.getOriginalPrice())
                .salePrice(homeItem.getSalePrice())
                .stockQuantity(homeItem.getStockQuantity())
                .createdAt(homeItem.getCreatedAt())
                .saleUpdatedAt(homeItem.getSaleUpdatedAt())
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
        return null;
    }
}
