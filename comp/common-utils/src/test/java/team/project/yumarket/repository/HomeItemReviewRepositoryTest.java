package team.project.yumarket.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import team.project.yumarket.model.entity.home.HomeItemReview;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeItemReviewRepositoryTest {

    @Autowired
    private HomeItemReviewRepository homeItemReviewRepository;

    @Autowired
    private HomeItemRepository homeItemRepository;

    @Autowired
    private UserRepository userRepository;

    // Mock data
    private Long userId = 2L;
    private Long homeItemId = 4L;
    private double grade = 4.5;

    @Test
    @DisplayName("create : success test")
    @Transactional
    @Rollback(value = false)
    void create1() {
        // given
        HomeItemReview newReview = createReview(grade);

        // when
        HomeItemReview savedReview = homeItemReviewRepository.save(newReview);

        // then
        Assertions.assertNotNull(savedReview);
        Assertions.assertEquals(savedReview.getHomeItem().getId(), homeItemId);
        Assertions.assertEquals(savedReview.getUser().getId(), userId);
        Assertions.assertEquals(savedReview.getGrade(), grade);
    }

    @Test
    @DisplayName("create : fail test (by invalid request)")
    @Transactional
    @Rollback(value = false)
    void create2() {
        // given
        HomeItemReview newReview = createReview(7.0);

        // when
        HomeItemReview savedReview = homeItemReviewRepository.save(newReview);

        // then
        Assertions.assertNotNull(savedReview);
        Assertions.assertEquals(savedReview.getHomeItem().getId(), homeItemId);
        Assertions.assertEquals(savedReview.getUser().getId(), userId);
        Assertions.assertEquals(savedReview.getGrade(), grade);
    }

    @Test
    @DisplayName("read : ")
    @Transactional
    void read1() {
        // when
        Optional<HomeItemReview> targetReview = homeItemReviewRepository.findById(5L);

        // then
        Assertions.assertNotNull(targetReview);
        targetReview.ifPresent(homeItemReview -> {
            System.out.println("목록 : " + homeItemReview.toString());

            Assertions.assertEquals(homeItemReview.getGrade(), grade);
            Assertions.assertEquals(homeItemReview.getUser().getId(), userId);
            Assertions.assertEquals(homeItemReview.getHomeItem().getId(), homeItemId);
        });
    }

    @Test
    @DisplayName("update : ")
    @Transactional
    @Rollback(value = false)
    void update() {
        // given
        Optional<HomeItemReview> targetReview = homeItemReviewRepository.findById(1L);

        Assertions.assertNotNull(targetReview); // assert not null

        // when
        targetReview.ifPresent(homeItemReview -> {
            homeItemReview.setGrade(5.0);
            homeItemReviewRepository.save(homeItemReview);
        });
    }

    @Test
    @DisplayName("delete : ")
    void delete() {

    }

    private HomeItemReview createReview(double grade) {
        return HomeItemReview.builder()
                .content("정말로 맛있어요")
                .grade(grade)
                .homeItem(homeItemRepository.getById(homeItemId))
                .user(userRepository.getById(userId))
                .build();
    }
}