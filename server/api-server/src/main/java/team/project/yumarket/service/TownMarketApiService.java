package team.project.yumarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.project.yumarket.ifs.ServiceCrudInterface;
import team.project.yumarket.model.entity.home.TownMarket;
import team.project.yumarket.network.dto.request.TownMarketRequestDto;
import team.project.yumarket.network.dto.response.HomeItemResponseDto;
import team.project.yumarket.network.dto.response.MarketReviewResponseDto;
import team.project.yumarket.network.dto.response.townMarket.TownMarketDetailResponseDto;
import team.project.yumarket.network.dto.response.townMarket.TownMarketResponseDto;
import team.project.yumarket.network.exception.EntityNotFoundException;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.repository.TownMarketRepository;
import team.project.yumarket.util.url.Urls;

import java.util.List;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/20
 */
@Service
@RequiredArgsConstructor
public class TownMarketApiService implements ServiceCrudInterface<TownMarket, TownMarketRequestDto, TownMarketResponseDto> {

    private final String REQUEST_URL = Urls.BASE_URL + Urls.TOWN_MARKET;

    // Injections
    public final TownMarketRepository townMarketRepository;

    @Override
    public ResponseEntity<CommunicationFormat> create(CommunicationFormat<TownMarketRequestDto> request) {
        TownMarketRequestDto requestBody = request.getData();
        TownMarket townMarket = requestToEntity(requestBody);

        // 동네마켓 등록 (예외 발생하지 않음) TODO 추후에 사장님의 authentication을 검사하여 예외를 던져주기 (사용자가 사장 자격은 있는가?)
        townMarketRepository.save(townMarket);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommunicationFormat.OK(REQUEST_URL)
                );
    }

    // summarized read
    @Override
    public ResponseEntity<CommunicationFormat<TownMarketResponseDto>> read(Long id) {
        return townMarketRepository.findById(id).map(townMarket ->
                ResponseEntity.status(HttpStatus.OK)
                        .body(response(REQUEST_URL + "/" + id, townMarket))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    // detail read
    public ResponseEntity<CommunicationFormat<TownMarketDetailResponseDto>> detailRead(
            Long id,
            List<MarketReviewResponseDto> marketReviewResponseDtoList,
            List<HomeItemResponseDto> homeItemResponseDtoList
    ) throws EntityNotFoundException {

        return townMarketRepository.findById(id).map(market ->
                ResponseEntity.status(HttpStatus.OK)
                        .body(responseDetail(REQUEST_URL + "/" + id, market,
                                marketReviewResponseDtoList, homeItemResponseDtoList)
                        )
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<TownMarketResponseDto>> update(CommunicationFormat<TownMarketRequestDto> request, Long id) {
        TownMarketRequestDto requestBody = request.getData();

        return townMarketRepository.findById(id).map(townMarket ->
                townMarket.setName(requestBody.getName() == null ? townMarket.getName() : requestBody.getName())
                        .setOpen(requestBody.getIsOpen() == null ? townMarket.isOpen() : requestBody.getIsOpen())
                        .setOpenTime(requestBody.getOpenTime() == null ? townMarket.getOpenTime() : requestBody.getOpenTime())
                        .setCloseTime(requestBody.getCloseTime() == null ? townMarket.getCloseTime() : requestBody.getCloseTime())
                        .setAddress(requestBody.getAddress() == null ? townMarket.getAddress() : requestBody.getAddress())
                        .setLatitude(requestBody.getLatitude() == null ? townMarket.getLatitude() : requestBody.getLatitude())
                        .setLongitude(requestBody.getLongitude() == null ? townMarket.getLongitude() : requestBody.getLongitude())
                        .setMarketImageUrl(requestBody.getMarketImageUrl() == null ? townMarket.getMarketImageUrl() : requestBody.getMarketImageUrl())
        ).map(townMarket -> townMarketRepository.save(townMarket)
        ).map(townMarket -> ResponseEntity.status(HttpStatus.OK)
                .body(response(REQUEST_URL + "/" + id, townMarket))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat> delete(Long id) {
        return townMarketRepository.findById(id).map(townMarket -> {
                    townMarketRepository.delete(townMarket);

                    return townMarket;
                }
        ).map(townMarket -> ResponseEntity.status(HttpStatus.OK)
                .body(CommunicationFormat.OK(REQUEST_URL + "/" + id))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<List<TownMarketResponseDto>>> search(Pageable pageable) {
        return null;
    }

    @Override
    public CommunicationFormat<TownMarketResponseDto> response(String url, TownMarket market) {
        return CommunicationFormat.OK(url, responseData(market));
    }

    // detail dto에 대한 response method
    public CommunicationFormat<TownMarketDetailResponseDto> responseDetail(
            String url, TownMarket market,
            List<MarketReviewResponseDto> marketReviewResponseDtoList,
            List<HomeItemResponseDto> homeItemResponseDtoList
    ) {
        return CommunicationFormat.OK(url, responseDetailData(market, marketReviewResponseDtoList, homeItemResponseDtoList));
    }

    @Override
    public TownMarketResponseDto responseData(TownMarket market) {
        return TownMarketResponseDto.builder()
                .id(market.getId())
                .name(market.getName())
                .isOpen(market.isOpen())
                .openTime(market.getOpenTime())
                .closeTime(market.getCloseTime())
                .address(market.getAddress())
                .latitude(market.getLatitude())
                .longitude(market.getLongitude())
                .marketImageUrl(market.getMarketImageUrl())
                .itemQuantity(market.getHomeItemList().size())
                .likeQuantity(market.getMarketLikeList().size())
                .reviewQuantity(market.getMarketReviewList().size())
                .build();
    }

    // detail dto에 대한 responseData method
    public TownMarketDetailResponseDto responseDetailData(
            TownMarket market,
            List<MarketReviewResponseDto> marketReviewResponseDtoList,
            List<HomeItemResponseDto> homeItemResponseDtoList
    ) {
        return TownMarketDetailResponseDto.builder()
                .id(market.getId())
                .name(market.getName())
                .isOpen(market.isOpen())
                .openTime(market.getOpenTime())
                .closeTime(market.getCloseTime())
                .address(market.getAddress())
                .latitude(market.getLatitude())
                .longitude(market.getLongitude())
                .marketImageUrl(market.getMarketImageUrl())
                .itemQuantity(market.getHomeItemList().size())
                .likeQuantity(market.getMarketLikeList().size())
                .reviewQuantity(market.getMarketReviewList().size())
                .homeItemResponseDtoList(homeItemResponseDtoList)
                .marketReviewResponseDtoList(marketReviewResponseDtoList)
                .build();
    }

    @Override
    public TownMarket requestToEntity(TownMarketRequestDto request) {
        return TownMarket.builder()
                .name(request.getName())
                .isOpen(request.getIsOpen())
                .openTime(request.getOpenTime())
                .closeTime(request.getCloseTime())
                .address(request.getAddress())
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .marketImageUrl(request.getMarketImageUrl())
                .build();
    }
}
