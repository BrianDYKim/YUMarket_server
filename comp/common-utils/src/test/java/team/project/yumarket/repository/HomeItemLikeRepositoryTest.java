package team.project.yumarket.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import team.project.yumarket.model.entity.User;
import team.project.yumarket.model.entity.home.HomeItem;
import team.project.yumarket.model.entity.home.HomeItemLike;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeItemLikeRepositoryTest {

    @Autowired
    private HomeItemLikeRepository homeItemLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HomeItemRepository homeItemRepository;

    // Mock data
    private Long userId = 2L;
    private Long homeItemId = 4L;

    @Test
    @DisplayName("create : ")
    @Transactional
    @Rollback(value = false)
    void create() {
        // given
        HomeItemLike newLike = createLike();

        // when
        HomeItemLike savedLike = homeItemLikeRepository.save(newLike);

        // then
        Assertions.assertNotNull(savedLike); // assert that not null
        Assertions.assertEquals(savedLike.getHomeItem().getId(), homeItemId);
        Assertions.assertEquals(savedLike.getUser().getId(), userId);
    }

    @Test
    @DisplayName("read : ")
    @Transactional
    void read() {
        // given, when
        Optional<HomeItemLike> targetLike = homeItemLikeRepository.findById(1L);

        // then
        Assertions.assertNotNull(targetLike); // not null
        targetLike.ifPresent(homeItemLike -> {
            Assertions.assertEquals(homeItemLike.getUser().getId(), userId);
            Assertions.assertEquals(homeItemLike.getHomeItem().getId(), homeItemId);
        });
    }

    @Test
    @DisplayName("update : ")
    void update() {

    }

    @Test
    @DisplayName("delete : ")
    void delete() {
        homeItemLikeRepository.findById(1L).ifPresent(homeItemLikeRepository::delete);
    }

    private HomeItemLike createLike() {
        return HomeItemLike.builder()
                .homeItem(homeItemRepository.getById(homeItemId))
                .user(userRepository.getById(userId))
                .build();
    }
}