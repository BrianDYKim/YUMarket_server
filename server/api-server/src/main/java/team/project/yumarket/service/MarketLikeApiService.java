package team.project.yumarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.project.yumarket.ifs.ServiceCrudInterface;
import team.project.yumarket.model.entity.home.MarketLike;
import team.project.yumarket.network.dto.request.MarketLikeRequestDto;
import team.project.yumarket.network.dto.response.MarketLikeResponseDto;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.repository.MarketLikeRepository;
import team.project.yumarket.repository.TownMarketRepository;
import team.project.yumarket.repository.UserRepository;
import team.project.yumarket.util.url.Urls;

import java.util.List;
import java.util.Optional;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/22
 */
@Service
@RequiredArgsConstructor
public class MarketLikeApiService implements ServiceCrudInterface<MarketLike, MarketLikeRequestDto, MarketLikeResponseDto> {

    private final MarketLikeRepository marketLikeRepository;
    private final TownMarketRepository townMarketRepository;
    private final UserRepository userRepository;

    private final String REQUEST_URL = Urls.BASE_URL + Urls.MARKET_LIKE;

    @Override
    public ResponseEntity<CommunicationFormat> create(CommunicationFormat<MarketLikeRequestDto> request) {
        MarketLikeRequestDto requestBody = request.getData();
        MarketLike marketLike = requestToEntity(requestBody);

        MarketLike newMarketLike = marketLikeRepository.save(marketLike);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommunicationFormat.OK(REQUEST_URL));
    }

    @Override
    public ResponseEntity<CommunicationFormat<MarketLikeResponseDto>> read(Long id) {
        Optional<MarketLike> targetLike = marketLikeRepository.findById(id);

        return targetLike.map(marketLike ->
                ResponseEntity.status(HttpStatus.OK)
                        .body(response(REQUEST_URL + "/" + id, marketLike))
        ).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommunicationFormat.ERROR(REQUEST_URL + "/" + id, "존재하지 않는 찜 정보입니다"))
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<MarketLikeResponseDto>> update(CommunicationFormat<MarketLikeRequestDto> request, Long id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(CommunicationFormat.ERROR(REQUEST_URL + "/" + id, "찜에 대한 update 기능은 제공되지 않습니다.")
                );
    }

    @Override
    public ResponseEntity<CommunicationFormat> delete(Long id) {
        return marketLikeRepository.findById(id).map(marketLike -> {
                    marketLikeRepository.delete(marketLike);

                    return ResponseEntity.status(HttpStatus.OK)
                            .body(CommunicationFormat.OK(REQUEST_URL + "/" + id));
                }
        ).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommunicationFormat.ERROR(REQUEST_URL + "/" + id, "존재하지 않는 찜 정보입니다."))
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<List<MarketLikeResponseDto>>> search(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(CommunicationFormat.ERROR(REQUEST_URL, "찜에 대한 paging 기능은 제공하지 않습니다.")
                );
    }

    @Override
    public CommunicationFormat<MarketLikeResponseDto> response(String url, MarketLike marketLike) {
        return CommunicationFormat.OK(url, responseData(marketLike));
    }

    @Override
    public MarketLikeResponseDto responseData(MarketLike marketLike) {
        return MarketLikeResponseDto.builder()
                .id(marketLike.getId())
                .townMarketId(marketLike.getTownMarket().getId())
                .userId(marketLike.getUser().getId())
                .createdAt(marketLike.getCreatedAt())
                .build();
    }

    private MarketLike requestToEntity(MarketLikeRequestDto request) {
        return MarketLike.builder()
                .user(userRepository.getById(request.getUserId()))
                .townMarket(townMarketRepository.getById(request.getTownMarketId()))
                .build();
    }
}
