package team.project.yumarket.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import team.project.yumarket.model.entity.home.TownMarket;

import javax.transaction.Transactional;
import java.util.Optional;

@WebAppConfiguration
@SpringBootTest
class TownMarketRepositoryTest {

    @Autowired
    private TownMarketRepository townMarketRepository;

    @Autowired
    private HomeItemRepository homeItemRepository;

    private TownMarket createMarket() {
        return TownMarket.builder()
                .name("엄마밥상")
                .isOpen(true)
                .latitude(128.0)
                .longitude(37.5)
                .marketImageUrl("https://picsum.photos/200")
                .build();
    }

    @Test
    @DisplayName("create : ")
    void create() {
        TownMarket townMarket = createMarket();

        TownMarket newMarket = townMarketRepository.save(townMarket);

        // then
        Assertions.assertEquals(townMarket.getName(), newMarket.getName());
    }

    @Test
    @DisplayName("read : ")
    @Transactional
    void read() {
        Optional<TownMarket> targetMarket = townMarketRepository.findById(2L);

        // then
        Assertions.assertNotNull(targetMarket);
        targetMarket.ifPresent(market -> {
            Assertions.assertEquals("영남상회", market.getName());
            Assertions.assertEquals(true, market.isOpen());

            market.getHomeItemList().forEach(item -> {
                Assertions.assertEquals(item.getTownMarket().getId(), market.getId());
            });

            market.getMarketReviewList().forEach(review -> {
                System.out.println(review.getContent());
            });
        });
    }

    @Test
    @DisplayName("update : ")
    @Transactional
    void update() {
        TownMarket market = townMarketRepository.getById(1L);
        market.setName("영남대상회");

        TownMarket savedMarket = townMarketRepository.save(market);

        // then
        Assertions.assertEquals("영남대상회", savedMarket.getName());
    }

    @Test
    @DisplayName("delete : cascade test")
    void delete() {
        townMarketRepository.deleteById(1L);

        Assertions.assertNull(townMarketRepository.findById(1L));
        for(long i = 0; i < 2; i++)
            Assertions.assertNull(homeItemRepository.findById(i));
    }

}