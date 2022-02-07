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
import team.project.yumarket.handler.GlobalExceptionHandler;
import team.project.yumarket.model.entity.User;
import team.project.yumarket.model.entity.home.MarketLike;
import team.project.yumarket.model.entity.home.TownMarket;
import team.project.yumarket.model.enums.Role;
import team.project.yumarket.repository.MarketLikeRepository;
import team.project.yumarket.repository.TownMarketRepository;
import team.project.yumarket.repository.UserRepository;
import team.project.yumarket.service.MarketLikeApiService;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MarketLikeApiControllerTest {

    @Mock
    private GlobalExceptionHandler baseBadResponseHandler;

    @InjectMocks
    private MarketLikeApiController marketLikeApiController;

    @InjectMocks
    private MarketLikeApiService marketLikeApiService;

    @Mock
    private MarketLikeRepository marketLikeRepository;

    @Mock
    private TownMarketRepository townMarketRepository;

    @Mock
    private UserRepository userRepository;

    public MockMvc mockMvc;

    @BeforeEach
    public void prepare() {
        marketLikeApiController.baseService = marketLikeApiService;
        mockMvc = MockMvcBuilders.standaloneSetup(marketLikeApiController)
                .setControllerAdvice(baseBadResponseHandler)
                .build();
    }

    @Test
    @DisplayName("read : read success test")
    void read1() throws Exception {
        // given
        doReturn(Optional.of(createMarketLike(1L))).when(marketLikeRepository).findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/town-market-like/1")
                ).andExpect(status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("read : read fail test")
    void read2() throws Exception {
        // given
        doReturn(Optional.empty()).when(marketLikeRepository).findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/town-market-like/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("delete : delete success test")
    void delete1() throws Exception {
        // given
        doReturn(Optional.of(createMarketLike(1L))).when(marketLikeRepository).findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/town-market-like/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("delete : delete fail test")
    void delete2() throws Exception {
        // given
        doReturn(Optional.empty()).when(marketLikeRepository).findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/town-market-like/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }



    private MarketLike createMarketLike(Long id) {
        return MarketLike.builder()
                .id(id)
                .townMarket(createTownMarket(1L))
                .user(createUser(1L))
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