package team.project.yumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.project.yumarket.model.entity.home.MarketLike;
import team.project.yumarket.model.entity.home.TownMarket;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/22
 */
@Repository
public interface MarketLikeRepository extends JpaRepository<MarketLike, Long> {

    // 동네마켓의 찜 개수를 반환해주는 method
    public int countAllByTownMarket(TownMarket townMarket);
}
