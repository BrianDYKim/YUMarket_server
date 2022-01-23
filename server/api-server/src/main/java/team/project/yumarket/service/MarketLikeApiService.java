package team.project.yumarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.project.yumarket.ifs.ServiceCrudInterface;
import team.project.yumarket.model.entity.home.MarketLike;
import team.project.yumarket.network.dto.request.MarketLikeRequestDto;
import team.project.yumarket.network.dto.response.MarketLikeResponseDto;
import team.project.yumarket.network.formats.CommunicationFormat;

import java.util.List;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/22
 */
@Service
@RequiredArgsConstructor
public class MarketLikeApiService implements ServiceCrudInterface<MarketLike, MarketLikeRequestDto, MarketLikeResponseDto> {

    @Override
    public ResponseEntity<CommunicationFormat<MarketLikeResponseDto>> create(CommunicationFormat<MarketLikeRequestDto> request) {
        return null;
    }

    @Override
    public ResponseEntity<CommunicationFormat<MarketLikeResponseDto>> read(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommunicationFormat<MarketLikeResponseDto>> update(CommunicationFormat<MarketLikeRequestDto> request) {
        return null;
    }

    @Override
    public ResponseEntity<CommunicationFormat<MarketLikeResponseDto>> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CommunicationFormat<List<MarketLikeResponseDto>>> search(Pageable pageable) {
        return null;
    }

    @Override
    public CommunicationFormat<MarketLikeResponseDto> response(String url, MarketLike data) {
        return null;
    }

    @Override
    public MarketLikeResponseDto responseData(MarketLike data) {
        return null;
    }
}
