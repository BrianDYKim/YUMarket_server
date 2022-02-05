package team.project.yumarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import team.project.yumarket.ifs.ServiceCrudInterface;
import team.project.yumarket.model.entity.home.HomeItemLike;
import team.project.yumarket.network.dto.request.homeItem.HomeItemLikeRequestDto;
import team.project.yumarket.network.dto.response.homeItem.HomeItemLikeResponseDto;
import team.project.yumarket.network.exception.EntityNotFoundException;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.repository.HomeItemLikeRepository;
import team.project.yumarket.repository.HomeItemRepository;
import team.project.yumarket.repository.UserRepository;
import team.project.yumarket.util.url.Urls;

import java.util.List;

/**
 * HomeItemLike에 대한 Api service logic을 모아둔 클래스
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/04
 */
@Service
@RequiredArgsConstructor
public class HomeItemLikeApiService implements ServiceCrudInterface<HomeItemLike, HomeItemLikeRequestDto, HomeItemLikeResponseDto> {

    // Dependency injections
    private final HomeItemLikeRepository homeItemLikeRepository;
    private final HomeItemRepository homeItemRepository;
    private final UserRepository userRepository;

    private final String REQUEST_URL = Urls.BASE_URL + Urls.HOME_ITEM_LIKE; // 요청 주소

    @Override
    public ResponseEntity<CommunicationFormat> create(CommunicationFormat<HomeItemLikeRequestDto> request) {
        HomeItemLikeRequestDto requestBody = request.getData();
        HomeItemLike homeItemLike = requestToEntity(requestBody);

        // 예외 처리
        if(userRepository.existsById(requestBody.getUserId()) && homeItemRepository.existsById(requestBody.getHomeItemId()))
            homeItemLikeRepository.save(homeItemLike);
        else
            throw new EntityNotFoundException("Entity is not found");

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommunicationFormat.OK(REQUEST_URL)
                );
    }

    @Override
    public ResponseEntity<CommunicationFormat<HomeItemLikeResponseDto>> read(Long id) {
        return homeItemLikeRepository.findById(id).map(homeItemLike -> ResponseEntity
                .status(HttpStatus.OK)
                .body(response(REQUEST_URL + "/" + id, homeItemLike))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    // HomeItemLike에 대해서는 update 기능을 지원하지 않는다.
    @Override
    public ResponseEntity<CommunicationFormat<HomeItemLikeResponseDto>> update(CommunicationFormat<HomeItemLikeRequestDto> request, Long id) throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException("Access is denied");
    }

    @Override
    public ResponseEntity<CommunicationFormat> delete(Long id) {
        return homeItemLikeRepository.findById(id).map(homeItemLike -> {
            homeItemLikeRepository.delete(homeItemLike);

            return homeItemLike;
        }
        ).map(homeItemLike -> ResponseEntity.status(HttpStatus.OK)
                .body(CommunicationFormat.OK(REQUEST_URL + "/" + id))
        ).orElseThrow(() -> new EntityNotFoundException("Entity is not found")
        );
    }

    @Override
    public ResponseEntity<CommunicationFormat<List<HomeItemLikeResponseDto>>> search(Pageable pageable) throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException("Access is denied");
    }

    @Override
    public CommunicationFormat<HomeItemLikeResponseDto> response(String url, HomeItemLike like) {
        return CommunicationFormat.OK(url, responseData(like));
    }

    @Override
    public HomeItemLikeResponseDto responseData(HomeItemLike like) {
        return HomeItemLikeResponseDto.builder()
                .id(like.getId())
                .userId(like.getUser().getId())
                .homeItemId(like.getHomeItem().getId())
                .build();
    }

    @Override
    public HomeItemLike requestToEntity(HomeItemLikeRequestDto request) {
        return HomeItemLike.builder()
                .user(userRepository.getById(request.getUserId()))
                .homeItem(homeItemRepository.getById(request.getHomeItemId()))
                .build();
    }
}
