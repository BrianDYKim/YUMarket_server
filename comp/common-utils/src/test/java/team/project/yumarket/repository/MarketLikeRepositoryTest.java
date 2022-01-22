package team.project.yumarket.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import team.project.yumarket.model.entity.User;
import team.project.yumarket.model.entity.home.MarketLike;
import team.project.yumarket.model.entity.home.TownMarket;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MarketLikeRepositoryTest {

    @Autowired
    private MarketLikeRepository marketLikeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TownMarketRepository townMarketRepository;

    private User user;
    private TownMarket townMarket;

    @BeforeEach
    private void prepare() {
        user = userRepository.getById(1L);
        townMarket = townMarketRepository.getById(2L);
    }

    private MarketLike createMarketLike() {
        return MarketLike.builder()
                .user(user)
                .townMarket(townMarket)
                .build();
    }

    @Test
    @DisplayName("create : ")
    @Transactional
    void create() {
        MarketLike marketLike = createMarketLike();

        MarketLike savedMarketLike = marketLikeRepository.save(marketLike);

        // Assertions
        Assertions.assertEquals(savedMarketLike.getUser(), user);
        Assertions.assertEquals(savedMarketLike.getTownMarket(), townMarket);
    }

    @Test
    @DisplayName("read : ")
    @Transactional
    void read() {
        Optional<MarketLike> marketLike = marketLikeRepository.findById(2L);

        // Assertions
        Assertions.assertNotNull(marketLike);
        marketLike.ifPresent(like -> {
            Assertions.assertEquals(like.getUser(), user);
            Assertions.assertEquals(like.getTownMarket(), townMarket);
            Assertions.assertEquals(like.getId(), 2L);
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