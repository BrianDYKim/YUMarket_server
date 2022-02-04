package team.project.yumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.yumarket.model.entity.home.HomeItemReview;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/04
 */
public interface HomeItemReviewRepository extends JpaRepository<HomeItemReview, Long> {

}
