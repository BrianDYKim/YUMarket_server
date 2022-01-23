package team.project.yumarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.project.yumarket.ifs.ServiceCrudInterface;
import team.project.yumarket.model.entity.home.MarketReview;
import team.project.yumarket.network.dto.request.MarketReviewRequestDto;
import team.project.yumarket.network.dto.response.MarketReviewResponseDto;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.repository.MarketReviewRepository;
import team.project.yumarket.repository.TownMarketRepository;
import team.project.yumarket.repository.UserRepository;
import team.project.yumarket.util.url.Urls;

import java.util.List;
import java.util.Optional;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/23
 */
@Service
@RequiredArgsConstructor
public class MarketReviewApiService implements ServiceCrudInterface<MarketReview, MarketReviewRequestDto, MarketReviewResponseDto> {

    // (baseUrl)/api/town-market/review
    private final String REQUEST_URL = Urls.BASE_URL + Urls.MARKET_REVIEW;

    // Dependency injections
    private final MarketReviewRepository marketReviewRepository;
    private final TownMarketRepository townMarketRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<CommunicationFormat> create(CommunicationFormat<MarketReviewRequestDto> request) {
        return null;
    }

    @Override
    public ResponseEntity<CommunicationFormat<MarketReviewResponseDto>> read(Long id) {
        Optional<MarketReview> targetReview = marketReviewRepository.findById(id);

        return targetReview.map(marketReview ->
                ResponseEntity.status(HttpStatus.OK)
                        .body(response(REQUEST_URL + "/" + id, marketReview))
        ).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommunicationFormat.ERROR(REQUEST_URL + "/" + id, "존재하지 않는 리뷰 정보입니다."))
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<MarketReviewResponseDto>> update(CommunicationFormat<MarketReviewRequestDto> request, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommunicationFormat> delete(Long id) {
        return marketReviewRepository.findById(id).map(marketReview -> {
                    marketReviewRepository.delete(marketReview);

                    return ResponseEntity.status(HttpStatus.OK)
                            .body(CommunicationFormat.OK(REQUEST_URL + "/" + id));
                }
        ).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommunicationFormat.ERROR(REQUEST_URL + "/" + id, "존재하지 않는 리뷰 정보입니다."))
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<List<MarketReviewResponseDto>>> search(Pageable pageable) {
        return null;
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
                .townMarketId(marketReview.getTownMarket().getId())
                .userId(marketReview.getUser().getId())
                .build();
    }

    private MarketReview requestToEntity(MarketReviewRequestDto request) {
        return MarketReview.builder()
                .grade(request.getGrade())
                .content(request.getContent())
                .user(userRepository.getById(request.getUserId()))
                .townMarket(townMarketRepository.getById(request.getTownMarketId()))
                .build();
    }
}