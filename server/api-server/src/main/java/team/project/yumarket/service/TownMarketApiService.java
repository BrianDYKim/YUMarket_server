package team.project.yumarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.project.yumarket.ifs.ServiceCrudInterface;
import team.project.yumarket.model.entity.home.TownMarket;
import team.project.yumarket.network.dto.request.TownMarketRequestDto;
import team.project.yumarket.network.dto.response.TownMarketResponseDto;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.repository.TownMarketRepository;
import team.project.yumarket.util.url.Urls;

import java.util.List;
import java.util.Optional;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/20
 */
@Service
@RequiredArgsConstructor
public class TownMarketApiService implements ServiceCrudInterface<TownMarket, TownMarketRequestDto, TownMarketResponseDto> {

    private final TownMarketRepository townMarketRepository;

    @Override
    public ResponseEntity<CommunicationFormat<TownMarketResponseDto>> create(CommunicationFormat<TownMarketRequestDto> request) {
        return null;
    }

    @Override
    public ResponseEntity<CommunicationFormat<TownMarketResponseDto>> read(Long id) {

        String requestUrl = Urls.TOWN_MARKET + "/" + id;
        Optional<TownMarket> targetMarket = townMarketRepository.findById(id);

        return targetMarket.map(townMarket ->
                ResponseEntity.status(HttpStatus.OK)
                        .body(response(requestUrl, townMarket))
        ).orElseGet(() ->
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(CommunicationFormat.ERROR(requestUrl, "잘못된 요청입니다"))
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<TownMarketResponseDto>> update(CommunicationFormat<TownMarketRequestDto> request) {
        return null;
    }

    @Override
    public ResponseEntity<CommunicationFormat<TownMarketResponseDto>> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommunicationFormat<List<TownMarketResponseDto>>> search(Pageable pageable) {
        return null;
    }

    @Override
    public CommunicationFormat<TownMarketResponseDto> response(String url, TownMarket market) {
        return CommunicationFormat.OK(url, responseData(market));
    }

    @Override
    public TownMarketResponseDto responseData(TownMarket market) {
        return TownMarketResponseDto.builder()
                .id(market.getId())
                .name(market.getName())
                .isOpen(market.isOpen())
                .latitude(market.getLatitude())
                .longitude(market.getLongitude())
                .marketImageUrl(market.getMarketImageUrl())
                .build();
    }
}
