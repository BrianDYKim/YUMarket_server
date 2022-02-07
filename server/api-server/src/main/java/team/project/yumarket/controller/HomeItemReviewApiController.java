package team.project.yumarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.project.yumarket.network.dto.request.homeItem.HomeItemReviewRequestDto;
import team.project.yumarket.network.dto.response.homeItem.HomeItemReviewResponseDto;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.service.HomeItemReviewApiService;
import team.project.yumarket.util.url.Urls;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/05
 */
@RestController
@RequestMapping(Urls.HOME_ITEM_REVIEW)
@RequiredArgsConstructor
public class HomeItemReviewApiController extends BaseController<HomeItemReviewRequestDto, HomeItemReviewResponseDto> {

    // Inject the Service
    private final HomeItemReviewApiService homeItemReviewApiService;

    // Post Construct
    @PostConstruct
    public void init() {
        this.baseService = homeItemReviewApiService;
    }

    // HomeItem의 id를 통해서 리뷰를 모두 가져오는 메소드
    // /api/item-review/lists?item_id=xx
    @GetMapping("/lists")
    public ResponseEntity<CommunicationFormat<List<HomeItemReviewResponseDto>>> searchByHomeItem(
            Pageable pageable, @RequestParam("item_id") Long homeItemId) {

        return homeItemReviewApiService.searchByHomeItem(pageable, homeItemId);
    }
}
