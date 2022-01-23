package team.project.yumarket.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        marketReviewApiController.baseService = marketReviewApiService; // post construct에 대한 처리
        mockMvc = MockMvcBuilders.standaloneSetup(marketReviewApiController).build();
    }

    @Test
    @DisplayName("create : ")
    void create() {

    }

    @Test
    @DisplayName("read : MarketReview read success test")
    void read1() throws Exception {
        // given
        doReturn(Optional.of(createReview(1L))).when(marketReviewRepository).findById(1L);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/town-market/review/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("read : MarketRwview read fail test")
    void read2() throws Exception {
        // given
        doReturn(Optional.empty()).when(marketReviewRepository).findById(1L);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/town-market/review/1")
        ).andExpect(status().isInternalServerError()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("update : ")
    void update() {

    }

    @Test
    @DisplayName("delete : delete success test")
    void delete1() throws Exception {
        // given
        doReturn(Optional.of(createReview(1L))).when(marketReviewRepository).findById(1L);

        // when and then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/town-market/review/1")
                ).andExpect(status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("delete : delete fail test")
    void delete2() throws Exception {
        // given
        doReturn(Optional.empty()).when(marketReviewRepository).findById(1L);

        // when and then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/town-market/review/1")
        ).andExpect(status().isInternalServerError()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    private MarketReview createReview(Long id) {
        return MarketReview.builder()
                .id(id)
                .townMarket(createMarket(1L))
                .user(createUser(1L))
                .grade(1.5)
                .content("국이 짜다")
                .build();
    }

    private User createUser(Long id) {
        return User.builder()
                .id(id)
                .name("홍길동")
                .role(Role.ROLE_USER)
                .build();
    }

    private TownMarket createMarket(Long id) {
        return TownMarket.builder()
                .id(id)
                .name("영남상회")
                .build();
    }
}