package team.project.yumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.project.yumarket.model.entity.home.MarketReview;
import team.project.yumarket.model.entity.home.TownMarket;

import java.util.List;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/22
 */
@Repository
public interface MarketReviewRepository extends JpaRepository<MarketReview, Long> {

    // townMarketId에 대해서 모든 Review들을 반환하주는 메소드
    List<MarketReview> findAllByTownMarket(TownMarket townMarket);
}