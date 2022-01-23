package team.project.yumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.project.yumarket.model.entity.home.MarketReview;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/22
 */
@Repository
public interface MarketReviewRepository extends JpaRepository<MarketReview, Long> {


}
