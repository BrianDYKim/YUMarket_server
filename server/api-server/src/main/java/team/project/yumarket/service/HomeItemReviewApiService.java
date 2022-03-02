package team.project.yumarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import team.project.yumarket.ifs.ServiceCrudInterface;
import team.project.yumarket.model.entity.home.HomeItemReview;
import team.project.yumarket.network.dto.request.homeItem.HomeItemReviewRequestDto;
import team.project.yumarket.network.dto.response.homeItem.HomeItemReviewResponseDto;
import team.project.yumarket.network.exception.EntityNotFoundException;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.network.formats.Pagination;
import team.project.yumarket.repository.HomeItemRepository;
import team.project.yumarket.repository.HomeItemReviewRepository;
import team.project.yumarket.repository.UserRepository;
import team.project.yumarket.util.url.Urls;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/05
 */
@Service
@RequiredArgsConstructor
public class HomeItemReviewApiService implements ServiceCrudInterface<HomeItemReview, HomeItemReviewRequestDto, HomeItemReviewResponseDto> {

    // Dependency injections
    private final HomeItemReviewRepository homeItemReviewRepository;
    private final HomeItemRepository homeItemRepository;
    private final UserRepository userRepository;

    private final String REQUEST_URL = Urls.BASE_URL + Urls.HOME_ITEM_REVIEW; // /api/item-review

    @Override
    public ResponseEntity<CommunicationFormat> create(CommunicationFormat<HomeItemReviewRequestDto> request) {
        HomeItemReviewRequestDto requestBody = request.getData();
        HomeItemReview homeItemReview = requestToEntity(requestBody);

        // 예외 검출 (실제 존재하는 사용자인가? 혹은 실제 존재하는 아이템에 대하여 리뷰를 작성하는가?)
        if (userRepository.existsById(requestBody.getUserId()) && homeItemRepository.existsById(requestBody.getHomeItemId()))
            homeItemReviewRepository.save(homeItemReview);
        else
            throw new EntityNotFoundException("Entity is not found");

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommunicationFormat.OK(REQUEST_URL)
                );
    }

    @Override
    public ResponseEntity<CommunicationFormat<HomeItemReviewResponseDto>> read(Long id) {
        return homeItemReviewRepository.findById(id).map(homeItemReview -> ResponseEntity
                .status(HttpStatus.OK)
                .body(response(REQUEST_URL + "/" + id, homeItemReview))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<HomeItemReviewResponseDto>> update(CommunicationFormat<HomeItemReviewRequestDto> request, Long id) throws HttpRequestMethodNotSupportedException {
        HomeItemReviewRequestDto requestBody = request.getData();

        return homeItemReviewRepository.findById(id).map(homeItemReview -> {
                    homeItemReview.setContent(requestBody.getContent() == null ? homeItemReview.getContent() : requestBody.getContent())
                            .setGrade(requestBody.getGrade() == null ? homeItemReview.getGrade() : requestBody.getGrade());

                    return homeItemReview;
                }
        ).map(homeItemReview -> homeItemReviewRepository.save(homeItemReview)
        ).map(homeItemReview -> ResponseEntity.status(HttpStatus.OK)
                .body(response(REQUEST_URL + "/" + id, homeItemReview))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat> delete(Long id) {
        return homeItemReviewRepository.findById(id).map(homeItemReview -> {
                    homeItemReviewRepository.delete(homeItemReview);

                    return homeItemReview;
                }
        ).map(homeItemReview -> ResponseEntity.status(HttpStatus.OK)
                .body(CommunicationFormat.OK(REQUEST_URL + "/" + id))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    // 일반적인 searching method는 지원하지 않습니다. -> 아이템별로 search 기능을 제공하도록 구현합니다.
    @Override
    public ResponseEntity<CommunicationFormat<List<HomeItemReviewResponseDto>>> search(Pageable pageable) throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException("Access is denied");
    }

    // HomeItem의 id를 통해서 HomeItem의 모든 review를 뽑아오는 메소드
    public ResponseEntity<CommunicationFormat<List<HomeItemReviewResponseDto>>> searchByHomeItem(Pageable pageable, Long homeItemId) {
        // 예외 처리
        if (!homeItemRepository.existsById(homeItemId))
            throw new EntityNotFoundException("Entity is not found");

        Page<HomeItemReview> reviewPage = homeItemReviewRepository.findAllByHomeItem(pageable, homeItemRepository.getById(homeItemId));

        List<HomeItemReviewResponseDto> reviewResponseList = reviewPage.stream().map(homeItemReview ->
                responseData(homeItemReview)
        ).collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPage(reviewPage.getTotalPages())
                .totalElements(reviewPage.getTotalElements())
                .currentPage(reviewPage.getNumber())
                .currentElements(reviewPage.getNumberOfElements())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommunicationFormat.OK(REQUEST_URL + "/lists?item_id=" + homeItemId, reviewResponseList, pagination)
                );
    }

    @Override
    public CommunicationFormat<HomeItemReviewResponseDto> response(String url, HomeItemReview review) {
        return CommunicationFormat.OK(url, responseData(review));
    }

    @Override
    public HomeItemReviewResponseDto responseData(HomeItemReview review) {
        return HomeItemReviewResponseDto.builder()
                .id(review.getId())
                .grade(review.getGrade())
                .content(review.getContent())
                .userId(review.getUser().getId())
                .homeItemId(review.getHomeItem().getId())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    @Override
    public HomeItemReview requestToEntity(HomeItemReviewRequestDto request) {
        return HomeItemReview.builder()
                .grade(request.getGrade())
                .content(request.getContent())
                .homeItem(homeItemRepository.getById(request.getHomeItemId()))
                .user(userRepository.getById(request.getUserId()))
                .build();
    }
}
