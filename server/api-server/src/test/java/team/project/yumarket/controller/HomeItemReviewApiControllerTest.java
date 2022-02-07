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
import team.project.yumarket.model.entity.home.HomeItemReview;
import team.project.yumarket.model.enums.Role;
import team.project.yumarket.repository.HomeItemRepository;
import team.project.yumarket.repository.HomeItemReviewRepository;
import team.project.yumarket.repository.UserRepository;
import team.project.yumarket.service.HomeItemReviewApiService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class HomeItemReviewApiControllerTest {

    @InjectMocks
    private HomeItemReviewApiController homeItemReviewApiController;

    @InjectMocks
    private HomeItemReviewApiService homeItemReviewApiService;

    @Mock
    private HomeItemReviewRepository homeItemReviewRepository;

    @Mock
    private HomeItemRepository homeItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GlobalExceptionHandler baseBadResponseHandler; // Exception Handler

    public MockMvc mockMvc;

    @BeforeEach
    public void prepare() {
        homeItemReviewApiController.baseService = homeItemReviewApiService;
        mockMvc = MockMvcBuilders.standaloneSetup(homeItemReviewApiController)
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

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/item-review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createInput(1L, 1L, 4.5, "정말로 맛있어요!")))
        ).andExpect(status().isOk()
        );
    }

    @Test
    @DisplayName("create : fail test (Entity is not found) -> 1번 유저가 존재하지 않는 경우의 테스트")
    void create2() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(false).when(userRepository).existsById(1L);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/item-review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createInput(1L, 1L, 4.5, "정말로 맛있어요!")))
        ).andExpect(status().isOk()
        );
    }

    @Test
    @DisplayName("read : read success test")
    void read1() throws Exception {
        // given
        doReturn(Optional.of(createReview(1L, 1L, 1L, 4.5))).when(homeItemReviewRepository).findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/item-review/1")
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("read : read fail test")
    void read2() throws Exception {
        // given
        doReturn(Optional.empty()).when(homeItemReviewRepository).findById(1L);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/item-review/1")
        ).andExpect(status().isOk()
        );
    }

    @Test
    @DisplayName("update : update success test")
    void update1() throws Exception {
        // given
        ObjectMapper mapper = new ObjectMapper();
        doReturn(Optional.of(createReview(1L, 1L, 1L, 4.5))).when(homeItemReviewRepository).findById(1L);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/item-review/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(createInput(1L, 1L, 1.0, "정말로 맛이 없어요.")))
        ).andExpect(status().isOk()
        ).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("delete : delete success test")
    void delete1() throws Exception {
        // given
        doReturn(Optional.of(createReview(1L, 1L, 1L, 4.5))).when(homeItemReviewRepository).findById(1L);

        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/item-review/1")
        ).andExpect(status().isOk()
        );
    }

    @Test
    @DisplayName("delete : delete fail test (Entity is not found)")
    void delete2() throws Exception {
        // given
        doReturn(Optional.empty()).when(homeItemReviewRepository).findById(1L);

        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/item-review/1")
        ).andExpect(status().isOk()
        );
    }

    // create input
    private Map<String, Map> createInput(Long userId, Long itemId, Double grade, String content) {
        Map<String, String> data = new HashMap<>();
        data.put("user_id", userId.toString());
        data.put("home_item_id", itemId.toString());
        data.put("grade", grade.toString());
        data.put("content", content);

        Map<String, Map> input = new HashMap<>();
        input.put("data", data);

        return input;
    }

    public HomeItemReview createReview(Long id, Long userId, Long itemId, Double grade) {
        return HomeItemReview.builder()
                .id(id)
                .user(createUser(userId))
                .homeItem(createItem(itemId))
                .grade(grade)
                .content("정말로 맛있어요!")
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