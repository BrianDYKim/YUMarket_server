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
import team.project.yumarket.model.entity.home.HomeItem;
import team.project.yumarket.model.entity.home.HomeItemLike;
import team.project.yumarket.model.enums.Role;
import team.project.yumarket.repository.HomeItemLikeRepository;
import team.project.yumarket.repository.HomeItemRepository;
import team.project.yumarket.repository.UserRepository;
import team.project.yumarket.service.HomeItemLikeApiService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
class HomeItemLikeApiControllerTest {

    @InjectMocks
    private HomeItemLikeApiController homeItemLikeApiController;

    @InjectMocks
    private HomeItemLikeApiService homeItemLikeApiService;

    @Mock
    private GlobalExceptionHandler baseBadResponseHandler;

    @Mock
    private HomeItemLikeRepository homeItemLikeRepository;

    @Mock
    private HomeItemRepository homeItemRepository;

    @Mock
    private UserRepository userRepository;

    public MockMvc mockMvc;

    // Mockito Test build 전에 실행이 되는 코드
    @BeforeEach
    public void prepare() {
        homeItemLikeApiController.baseService = homeItemLikeApiService;
        mockMvc = MockMvcBuilders.standaloneSetup(homeItemLikeApiController)
                .setControllerAdvice(baseBadResponseHandler)
                .build();
    }

    @Test
    @DisplayName("create : create success test")
    void create1() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(true).when(userRepository).existsById(1L);
        doReturn(true).when(homeItemRepository).existsById(1L);

        // when and then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/item-like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createInput(1L, 1L)))
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("create : create fail test")
    void create2() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(true).when(userRepository).existsById(1L);
        doReturn(false).when(homeItemRepository).existsById(1L);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/item-like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createInput(1L, 1L)))
        ).andExpect(status().isOk()
        );
    }

    @Test
    @DisplayName("read : read success test")
    void read1() throws Exception {
        // given
        doReturn(Optional.of(createLike(1L, 1L, 1L))).when(homeItemLikeRepository).findById(1L);

        // when and then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/item-like/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("read : read fail test")
    void read2() throws Exception {
        // given
        doReturn(Optional.empty()).when(homeItemLikeRepository).findById(1L);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/item-like/1")
        ).andExpect(status().isOk()
        );
    }

    @Test
    @DisplayName("update : update fail test (not supported method)")
    void update() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/item-like/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createInput(1L, 1L)))
        ).andExpect(status().isOk()
        );
    }

    @Test
    @DisplayName("delete : delete success test")
    void delete1() throws Exception {
        // given
        doReturn(Optional.of(createLike(1L, 1L, 1L))).when(homeItemLikeRepository).findById(1L);

        // when and then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/item-like/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("delete : delete fail test (Entity is not found)")
    void delete2() throws Exception {
        // given
        doReturn(Optional.empty()).when(homeItemLikeRepository).findById(1L);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/item-like/1")
        ).andExpect(status().isOk()
        );
    }

    // create input
    private Map<String, Map> createInput(Long userId, Long itemId) {
        Map<String, String> data = new HashMap<>();
        data.put("user_id", userId.toString());
        data.put("home_item_id", itemId.toString());

        Map<String, Map> input = new HashMap<>();
        input.put("data", data);

        return input;
    }

    public HomeItemLike createLike(Long id, Long userId, Long itemId) {
        return HomeItemLike.builder()
                .id(id)
                .user(createUser(userId))
                .homeItem(createItem(itemId))
                .build();
    }

    private User createUser(Long id) {
        return User.builder()
                .id(id)
                .name("홍길동")
                .role(Role.ROLE_USER)
                .build();
    }

    private HomeItem createItem(Long id) {
        return HomeItem.builder()
                .id(id)
                .name("맛있는 케아크")
                .build();
    }
}