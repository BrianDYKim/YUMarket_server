package team.project.yumarket.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import team.project.yumarket.model.entity.User;
import team.project.yumarket.model.entity.home.MarketReview;
import team.project.yumarket.model.entity.home.TownMarket;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MarketReviewRepositoryTest {

    @Autowired
    private MarketReviewRepository marketReviewRepository;

    @Autowired
    private TownMarketRepository townMarketRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    private TownMarket townMarket;

    @BeforeEach
    public void prepare() {
        user = userRepository.getById(2L);
        townMarket = townMarketRepository.getById(2L);
    }

    private MarketReview createReview() {
        return MarketReview.builder()
                .grade(1.5)
                .content("최악이네요")
                .townMarket(townMarket)
                .user(user)
                .build();
    }

    @Test
    @DisplayName("create : ")
    @Transactional
    @Rollback(value = false)
    void create() {
        MarketReview review = createReview();
        MarketReview savedReview = marketReviewRepository.save(review);

        // Assertion
        Assertions.assertEquals(savedReview.getUser(), user);
        Assertions.assertEquals(savedReview.getTownMarket(), townMarket);
        Assertions.assertEquals(savedReview.getGrade(), 1.5);
    }

    @Test
    @DisplayName("read : ")
    @Transactional
    void read() {
        Optional<MarketReview> targetReview = marketReviewRepository.findById(1L);

        // Assertion
        Assertions.assertNotNull(targetReview);
        targetReview.ifPresent(review -> {
            Assertions.assertEquals(review.getUser(), user);
            Assertions.assertEquals(review.getTownMarket(), townMarket);
        });
    }

    @Test
    @DisplayName("update : ")
    void update() {

    }

    @Test
    @DisplayName("delete : ")
    void delete() {

    }
}