package team.project.yumarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import team.project.yumarket.ifs.ServiceCrudInterface;
import team.project.yumarket.model.entity.home.MarketReview;
import team.project.yumarket.network.dto.request.MarketReviewRequestDto;
import team.project.yumarket.network.dto.response.MarketReviewResponseDto;
import team.project.yumarket.network.exception.EntityNotFoundException;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.repository.MarketReviewRepository;
import team.project.yumarket.repository.TownMarketRepository;
import team.project.yumarket.repository.UserRepository;
import team.project.yumarket.util.url.Urls;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/25
 */
@Service
@RequiredArgsConstructor
public class MarketReviewApiService implements ServiceCrudInterface<MarketReview, MarketReviewRequestDto, MarketReviewResponseDto> {

    private final String REQUEST_URL = Urls.BASE_URL + Urls.MARKET_REVIEW;

    private final MarketReviewRepository marketReviewRepository;
    private final TownMarketRepository townMarketRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<CommunicationFormat> create(CommunicationFormat<MarketReviewRequestDto> request) {
        MarketReviewRequestDto requestBody = request.getData();
        MarketReview marketReview = requestToEntity(requestBody);

        // Entity not found에 대한 예외 던지기
        if (townMarketRepository.existsById(requestBody.getTownMarketId()) && userRepository.existsById(requestBody.getUserId()))
            marketReviewRepository.save(marketReview);
        else
            throw new EntityNotFoundException("Entity is not found");

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommunicationFormat.OK(REQUEST_URL)
                );
    }

    @Override
    public ResponseEntity<CommunicationFormat<MarketReviewResponseDto>> read(Long id) {
        return marketReviewRepository.findById(id).map(marketReview ->
                ResponseEntity.status(HttpStatus.OK)
                        .body(response(REQUEST_URL + "/" + id, marketReview))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<MarketReviewResponseDto>> update(CommunicationFormat<MarketReviewRequestDto> request, Long id) throws HttpRequestMethodNotSupportedException {
        MarketReviewRequestDto requestBody = request.getData();

        return marketReviewRepository.findById(id).map(marketReview -> {
                    marketReview.setGrade(requestBody.getGrade() == null? marketReview.getGrade() : requestBody.getGrade())
                            .setContent(requestBody.getContent() == null? marketReview.getContent() : requestBody.getContent());

                    return marketReview;
                }
        ).map(marketReviewRepository::save
        ).map(marketReview -> ResponseEntity.status(HttpStatus.OK)
                .body(response(REQUEST_URL + "/" + id, marketReview))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat> delete(Long id) {
        return marketReviewRepository.findById(id).map(marketReview -> {
                    marketReviewRepository.delete(marketReview);

                    return ResponseEntity.status(HttpStatus.OK)
                            .body(CommunicationFormat.OK(REQUEST_URL + "/" + id));
                }
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<List<MarketReviewResponseDto>>> search(Pageable pageable) throws HttpRequestMethodNotSupportedException {
        // 지원하지 않음
        throw new HttpRequestMethodNotSupportedException("Access is denied(this method is not supported)");
    }

    public ResponseEntity<CommunicationFormat<List<MarketReviewResponseDto>>> readReviewsByMarketId(Long townMarketId) {
        // 예외 검출
        if (!townMarketRepository.existsById(townMarketId))
            throw new EntityNotFoundException("TownMarket does not exist!");

        List<MarketReview> reviews = marketReviewRepository.findAllByTownMarket(townMarketRepository.getById(townMarketId));

        List<MarketReviewResponseDto> responseList = reviews.stream().map(marketReview -> responseData(marketReview)
        ).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommunicationFormat.OK(REQUEST_URL + "/lists?market-id=" + townMarketId, responseList)
                );
    }

    @Override
    public CommunicationFormat<MarketReviewResponseDto> response(String url, MarketReview marketReview) {
        return CommunicationFormat.OK(url, responseData(marketReview));
    }

    @Override
    public MarketReviewResponseDto responseData(MarketReview marketReview) {
        return MarketReviewResponseDto.builder()
                .id(marketReview.getId())
                .grade(marketReview.getGrade())
                .content(marketReview.getContent())
                .createdAt(marketReview.getCreatedAt())
                .updatedAt(marketReview.getUpdatedAt())
                .userId(marketReview.getUser().getId())
                .townMarketId(marketReview.getTownMarket().getId())
                .build();
    }

    @Override
    public MarketReview requestToEntity(MarketReviewRequestDto request) {
        return MarketReview.builder()
                .grade(request.getGrade())
                .content(request.getContent())
                .townMarket(townMarketRepository.getById(request.getTownMarketId()))
                .user(userRepository.getById(request.getUserId()))
                .build();
    }
}
