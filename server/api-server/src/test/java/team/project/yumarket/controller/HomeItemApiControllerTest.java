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
import team.project.yumarket.model.entity.home.TownMarket;
import team.project.yumarket.repository.HomeItemRepository;
import team.project.yumarket.repository.TownMarketRepository;
import team.project.yumarket.service.HomeItemApiService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class HomeItemApiControllerTest {

    @InjectMocks
    private HomeItemApiController homeItemApiController;

    @InjectMocks
    private HomeItemApiService homeItemApiService;

    @Mock
    private HomeItemRepository homeItemRepository;

    @Mock
    private TownMarketRepository townMarketRepository;

    @Mock
    private BaseBadResponseHandler baseBadResponseHandler;

    public MockMvc mockMvc;

    @BeforeEach
    public void prepare() {
        homeItemApiController.baseService = homeItemApiService;
        mockMvc = MockMvcBuilders.standaloneSetup(homeItemApiController)
                .setControllerAdvice(baseBadResponseHandler)
                .build();
    }

    @Test
    @DisplayName("create : create success test")
    void create1() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(true).when(townMarketRepository).existsById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/item")
                .content(mapper.writeValueAsString(createInput(1L)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("create : create fail test (Entity is not found)")
    void create2() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(false).when(townMarketRepository).existsById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/item")
                .content(mapper.writeValueAsString(createInput(1L)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("read : read success test")
    void read1() throws Exception {
        // given
        doReturn(createMarket(1L)).when(townMarketRepository).getById(1L);
        doReturn(Optional.of(createItem(1L, 1L))).when(homeItemRepository).findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/item/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("read : read fail test(Entity is not found)")
    void read2() throws Exception {
        // given
        doReturn(Optional.empty()).when(homeItemRepository).findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/item/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("update : update success test")
    void update1() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(createMarket(1L)).when(townMarketRepository).getById(1L);
        doReturn(Optional.of(createItem(1L, 1L))).when(homeItemRepository).findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/item/1")
                .content(mapper.writeValueAsString(createInput(1L)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("update : update fail test")
    void update2() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(Optional.empty()).when(homeItemRepository).findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/item/1")
                .content(mapper.writeValueAsString(createInput(1L)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("delete : ")
    void delete() {

    }

    private Map<String, Map> createInput(Long townMarketId) {
        Map<String, String> data = new HashMap<>();
        data.put("detail_category", "ALL");
        data.put("name", "초코칩");
        data.put("original_price", "2000");
        data.put("sale_price", "1800");
        data.put("stock_quantity", "10");
        data.put("town_market_id", townMarketId.toString());

        Map<String, Map> input = new HashMap<>();
        input.put("data", data);

        return input;
    }

    private HomeItem createItem(Long id, Long townMarketId) {
        return HomeItem.builder()
                .id(id)
                .townMarket(townMarketRepository.getById(townMarketId))
                .name("맛있는 케아크")
                .build();
    }

    private TownMarket createMarket(Long id) {
        return TownMarket.builder()
                .id(id)
                .name("빅마트")
                .build();
    }
}