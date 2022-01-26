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
import team.project.yumarket.handler.BaseBadResponseHandler;
import team.project.yumarket.model.entity.home.HomeItem;
import team.project.yumarket.model.entity.home.MarketLike;
import team.project.yumarket.model.entity.home.MarketReview;
import team.project.yumarket.model.entity.home.TownMarket;
import team.project.yumarket.repository.TownMarketRepository;
import team.project.yumarket.service.TownMarketApiService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TownMarketApiControllerTest {

    @InjectMocks
    private TownMarketApiController townMarketApiController;

    @InjectMocks
    private TownMarketApiService townMarketApiService;

    @Mock
    private TownMarketRepository townMarketRepository;

    @Mock
    private BaseBadResponseHandler baseBadResponseHandler;

    public MockMvc mockMvc;

    @BeforeEach
    public void prepare() {
        townMarketApiController.baseService = townMarketApiService;
        mockMvc = MockMvcBuilders.standaloneSetup(townMarketApiController)
                .setControllerAdvice(baseBadResponseHandler)
                .build();
    }

    @Test
    @DisplayName("create : create success test")
    void create() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/town-market")
                .content(mapper.writeValueAsString(createInputData()))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        );
    }

    @Test
    @DisplayName("read : read success test")
    void read1() throws Exception {
        // given
        doReturn(Optional.of(createTownMarket(1L))).when(townMarketRepository).findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/town-market/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("read : read fail test(Entity is not found)")
    void read2() throws Exception {
        // given
        doReturn(Optional.empty()).when(townMarketRepository).findById(1L);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/town-market/1")
        ).andExpect(status().isOk()
        );
    }

    @Test
    @DisplayName("update : ")
    void update() {

    }

    @Test
    @DisplayName("delete : ")
    void delete() {

    }

    // create test data 생성
    private Map<String, Map> createInputData() {
        Map<String, String> data = new HashMap<>();
        data.put("name", "오공복이");
        data.put("address", "경산시 조영동");
        data.put("open_time", LocalTime.of(6, 0, 0).toString());
        data.put("close_time", LocalTime.of(18, 0, 0).toString());

        Map<String, Map> input = new HashMap<>();
        input.put("data", data);

        return input;
    }

    // 아무 정보도 없는 townMarket을 생성하는 메소드
    private TownMarket createInitialTownMarket(Long id) {

        return TownMarket.builder()
                .id(id)
                .name("오공복이")
                .build();
    }

    private TownMarket createTownMarket(Long id) {
        return createInitialTownMarket(id)
                .setMarketLikeList(createLikeList(List.of(1L, 2L, 3L), id))
                .setHomeItemList(createHomeItemList(List.of(1L, 2L, 3L), id))
                .setMarketReviewList(createReviewList(List.of(1L, 2L, 3L), id));
    }

    private List<MarketLike> createLikeList(List<Long> idList, Long marketId) {
        return idList.stream().map(id ->
                MarketLike.builder()
                        .id(id)
                        .townMarket(createInitialTownMarket(marketId))
                        .build()
        ).collect(Collectors.toList());
    }

    private List<HomeItem> createHomeItemList(List<Long> idList, Long marketId) {
        return idList.stream().map(id ->
                HomeItem.builder()
                        .id(id)
                        .townMarket(createInitialTownMarket(marketId))
                        .build()
        ).collect(Collectors.toList());
    }

    private List<MarketReview> createReviewList(List<Long> idList, Long marketId) {
        return idList.stream().map(id ->
                MarketReview.builder()
                        .id(id)
                        .townMarket(createInitialTownMarket(marketId))
                        .build()
        ).collect(Collectors.toList());
    }

}