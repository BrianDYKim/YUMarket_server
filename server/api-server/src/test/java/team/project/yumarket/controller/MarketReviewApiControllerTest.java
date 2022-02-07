package team.project.yumarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import team.project.yumarket.handler.GlobalExceptionHandler;
import team.project.yumarket.model.entity.User;
import team.project.yumarket.model.entity.home.MarketReview;
import team.project.yumarket.model.entity.home.TownMarket;
import team.project.yumarket.model.enums.Role;
import team.project.yumarket.repository.MarketReviewRepository;
import team.project.yumarket.repository.TownMarketRepository;
import team.project.yumarket.repository.UserRepository;
import team.project.yumarket.service.MarketReviewApiService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MarketReviewApiControllerTest {

    @InjectMocks
    private MarketReviewApiController marketReviewApiController;

    @InjectMocks
    private MarketReviewApiService marketReviewApiService;

    @Mock
    private GlobalExceptionHandler handler;

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
        mockMvc = MockMvcBuilders.standaloneSetup(marketReviewApiController)
                .setControllerAdvice(handler)
                .build();
    }

    @Test
    @DisplayName("create : create success test")
    void create1() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(true).when(townMarketRepository).existsById(1L);
        doReturn(true).when(userRepository).existsById(1L);
        Map<String, Map> input = createRequest(1.5, 1L, 1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/town-market-review")
                .content(mapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("create : create fail test (Entity is not found)")
    void create2() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(true).when(townMarketRepository).existsById(1L);
        doReturn(false).when(userRepository).existsById(1L);
        Map<String, Map> input = createRequest(1.5, 1L, 1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/town-market-review")
                .content(mapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("create : create fail test (invalid)")
    void create3() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(true).when(townMarketRepository).existsById(1L);
        doReturn(true).when(userRepository).existsById(1L);
        Map<String, Map> input = createRequest(100.0, 1L, 1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/town-market-review")
                .content(mapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("read : read success test")
    void read1() throws Exception {
        // given
        doReturn(createUser(1L)).when(userRepository).getById(1L);
        doReturn(createMarket(1L)).when(townMarketRepository).getById(1L);
        doReturn(Optional.of(createReview(1L, 1.5, 1L, 1L))).when(marketReviewRepository)
                .findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/town-market-review/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("read : read fail test (Entity is not found)")
    void read2() throws Exception {
        // given
        doReturn(Optional.empty()).when(marketReviewRepository).findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/town-market-review/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("update : update success test")
    void update1() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(createUser(1L)).when(userRepository).getById(1L);
        doReturn(createMarket(1L)).when(townMarketRepository).getById(1L);
        doReturn(Optional.of(createReview(1L, 1.5, 1L, 1L))).when(marketReviewRepository)
                .findById(1L);
        Map<String, Map> input = createRequest(2.0, 1L, 1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/town-market-review/1")
                .content(mapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("update : update fail test (Invalid input data)")
    void update2() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(createUser(1L)).when(userRepository).getById(1L);
        doReturn(createMarket(1L)).when(townMarketRepository).getById(1L);
        doReturn(Optional.of(createReview(1L, 1.5, 1L, 1L))).when(marketReviewRepository)
                .findById(1L);
        Map<String, Map> input = createRequest(7.0, 1L, 1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/town-market-review/1")
                .content(mapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("update : update fail test (Entity not found)")
    void update3() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(Optional.empty()).when(marketReviewRepository).findById(1L);
        Map<String, Map> input = createRequest(2.0, 1L, 1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/town-market-review/1")
                .content(mapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("delete : delete success test")
    void delete1() throws Exception {
        // given
        doReturn(createUser(1L)).when(userRepository).getById(1L);
        doReturn(createMarket(1L)).when(townMarketRepository).getById(1L);
        doReturn(Optional.of(createReview(1L, 1.5, 1L, 1L))).when(marketReviewRepository)
                .findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/town-market-review/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("delete : delete fail test(Entity is not found)")
    void delete2() throws Exception {
        // given
        doReturn(Optional.empty()).when(marketReviewRepository).findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/town-market-review/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("search : search fail test(Not supported method)")
    void search() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/town-market-review?page=0")
        ).andExpect(status().isOk()
        ).andReturn();
    }

    private Map<String, Map> createRequest(Double grade, Long userId, Long marketId) {
        Map<String, String> data = new HashMap<>();
        data.put("user_id", userId.toString());
        data.put("town_market_id", marketId.toString());
        data.put("content", "맛이 없어요");
        data.put("grade", grade.toString());

        Map<String, Map> input = new HashMap<>();
        input.put("data", data);

        return input;
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