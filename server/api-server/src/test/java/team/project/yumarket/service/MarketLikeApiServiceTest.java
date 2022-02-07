package team.project.yumarket.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import team.project.yumarket.model.entity.User;
import team.project.yumarket.model.entity.home.MarketLike;
import team.project.yumarket.model.entity.home.TownMarket;
import team.project.yumarket.model.enums.Role;
import team.project.yumarket.network.dto.response.townMarket.MarketLikeResponseDto;
import team.project.yumarket.network.formats.CommunicationFormat;
import team.project.yumarket.repository.MarketLikeRepository;
import team.project.yumarket.repository.TownMarketRepository;
import team.project.yumarket.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MarketLikeApiServiceTest {

    @InjectMocks
    private MarketLikeApiService marketLikeApiService;

    @Mock
    private MarketLikeRepository marketLikeRepository;

    @Mock
    private TownMarketRepository townMarketRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("create : ")
    void create() {

    }

    @Test
    @DisplayName("read : MarketLike Service success read Test")
    void read1() {
        // given
        doReturn(createTownMarket(1L)).when(townMarketRepository).getById(1L);
        doReturn(createUser(1L)).when(userRepository).getById(1L);
        doReturn(Optional.of(createMarketLike(1L))).when(marketLikeRepository).findById(1L);

        // when
        ResponseEntity<CommunicationFormat<MarketLikeResponseDto>> result = marketLikeApiService.read(1L);

        // then
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(result.getBody().getData().getUserId(), 1L);
    }

    @Test
    @DisplayName("read : MarketLike Service Fail read Test")
    void read2() {
        // given
        doReturn(Optional.empty()).when(marketLikeRepository).findById(1L);

        // when
        ResponseEntity<CommunicationFormat<MarketLikeResponseDto>> result = marketLikeApiService.read(1L);

        // then
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("update : ")
    void update() {

    }

    @Test
    @DisplayName("delete : MarketLike Service delete success test")
    void delete1() {
        // given
        doReturn(createTownMarket(1L)).when(townMarketRepository).getById(1L);
        doReturn(createUser(1L)).when(userRepository).getById(1L);
        doReturn(Optional.of(createMarketLike(1L))).when(marketLikeRepository).findById(1L);

        // when
        ResponseEntity<CommunicationFormat> result = marketLikeApiService.delete(1L);

        // then
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("delete : MarketLike Service delete fail test")
    void delete2() {
        // given
        doReturn(Optional.empty()).when(marketLikeRepository).findById(1L);

        // when
        ResponseEntity<CommunicationFormat> result = marketLikeApiService.delete(1L);

        // then
        assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    private MarketLike createMarketLike(Long id) {
        return MarketLike.builder()
                .id(id)
                .townMarket(townMarketRepository.getById(1L))
                .user(userRepository.getById(1L))
                .build();
    }

    private TownMarket createTownMarket(Long id) {
        return TownMarket.builder()
                .id(id)
                .name("영남상회")
                .build();
    }

    private User createUser(Long id) {
        return User.builder()
                .id(id)
                .name("홍길동")
                .role(Role.ROLE_USER)
                .build();
    }

}