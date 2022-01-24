package team.project.yumarket.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team.project.yumarket.model.entity.User;
import team.project.yumarket.model.entity.home.MarketReview;
import team.project.yumarket.model.entity.home.TownMarket;
import team.project.yumarket.model.enums.Role;
import team.project.yumarket.repository.MarketReviewRepository;
import team.project.yumarket.repository.TownMarketRepository;
import team.project.yumarket.repository.UserRepository;
import team.project.yumarket.service.MarketReviewApiService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MarketReviewApiControllerTest {

    @InjectMocks
    private MarketReviewApiController marketReviewApiController;

    @InjectMocks
    private MarketReviewApiService marketReviewApiService;

    @Mock
    private MarketReviewRepository marketReviewRepository;

    @Mock
    private TownMarketRepository townMarketRepository;

    @Mock
    private UserRepository userRepository;

    public MockMvc mockMvc;

    @BeforeEach
    public void prepare() {
        marketReviewApiController.baseService = marketReviewApiService;
        mockMvc = MockMvcBuilders.standaloneSetup(marketReviewApiController).build();
    }

    private MarketReview createReview(Long id, double grade, Long userId, Long townMarketId) {
        return MarketReview.builder()
                .id(id)
                .grade(grade)
                .content("맛이 없어요")
                .user(userRepository.getById(userId))
                .townMarket(townMarketRepository.getById(townMarketId))
                .build();
    }

    private TownMarket createMarket(Long id) {
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