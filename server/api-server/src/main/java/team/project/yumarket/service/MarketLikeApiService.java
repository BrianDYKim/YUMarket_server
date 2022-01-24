package team.project.yumarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import team.project.yumarket.ifs.ServiceCrudInterface;
import team.project.yumarket.model.entity.home.MarketLike;
import team.project.yumarket.network.dto.request.MarketLikeRequestDto;
import team.project.yumarket.network.dto.response.MarketLikeResponseDto;
import team.project.yumarket.network.exception.EntityNotFoundException;
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

        System.out.println(request);
        System.out.println(request.getData());

        MarketLikeRequestDto requestBody = request.getData();
        MarketLike marketLike = requestToEntity(requestBody);

        // 예외 검출
        if (townMarketRepository.existsById(requestBody.getTownMarketId()) && userRepository.existsById(requestBody.getUserId()))
            marketLikeRepository.save(marketLike);
        else
            throw new EntityNotFoundException("Entity is not found");

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommunicationFormat.OK(REQUEST_URL)
                );
    }

    @Override
    public ResponseEntity<CommunicationFormat<MarketLikeResponseDto>> read(Long id) {
        Optional<MarketLike> targetLike = marketLikeRepository.findById(id);

        return targetLike.map(marketLike ->
                ResponseEntity.status(HttpStatus.OK)
                        .body(response(REQUEST_URL + "/" + id, marketLike))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<MarketLikeResponseDto>> update(CommunicationFormat<MarketLikeRequestDto> request, Long id) throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException("Access is denied");
    }

    @Override
    public ResponseEntity<CommunicationFormat> delete(Long id) {
        return marketLikeRepository.findById(id).map(marketLike -> {
                    marketLikeRepository.delete(marketLike);

                    return ResponseEntity.status(HttpStatus.OK)
                            .body(CommunicationFormat.OK(REQUEST_URL + "/" + id));
                }
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<List<MarketLikeResponseDto>>> search(Pageable pageable) throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException("Access is denied");
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

    @Override
    public MarketLike requestToEntity(MarketLikeRequestDto request) {
        return MarketLike.builder()
                .user(userRepository.getById(request.getUserId()))
                .townMarket(townMarketRepository.getById(request.getTownMarketId()))
                .build();
    }
}
