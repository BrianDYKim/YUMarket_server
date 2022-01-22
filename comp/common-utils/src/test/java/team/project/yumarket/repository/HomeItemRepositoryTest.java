package team.project.yumarket.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import team.project.yumarket.model.entity.home.HomeItem;
import team.project.yumarket.model.enums.home.DetailCategory;

import javax.transaction.Transactional;

import java.util.Optional;

@SpringBootTest
@WebAppConfiguration
class HomeItemRepositoryTest {

    @Autowired
    private HomeItemRepository homeItemRepository;

    @Autowired
    private TownMarketRepository townMarketRepository;

    private HomeItem createItem() {
        return HomeItem.builder()
                .detailCategory(DetailCategory.FAST_FOOD)
                .itemImageUrl("https://picsum.photos/200")
                .name("불새버거")
                .originalPrice(4300)
                .salePrice(4300)
                .stockQuantity(10)
                .townMarket(townMarketRepository.getById(1L))
                .build();
    }

    @Test
    @DisplayName("create : ")
    @Transactional
    @Rollback(value = false)
    void create() {
        HomeItem homeItem = createItem();

        HomeItem item = homeItemRepository.save(homeItem);

        // then
        Assertions.assertEquals("불새버거", homeItem.getName());
        Assertions.assertEquals(DetailCategory.FAST_FOOD, item.getDetailCategory());
        Assertions.assertEquals(townMarketRepository.getById(1L), item.getTownMarket());
    }

    @Test
    @DisplayName("read : ")
    void read() {
        Optional<HomeItem> targetItem = homeItemRepository.findById(1L);

        // then
        Assertions.assertNotNull(targetItem);
        targetItem.ifPresent(item -> {
            Assertions.assertEquals("핫크리스피버거", item.getName());
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