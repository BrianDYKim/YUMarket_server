package team.project.yumarket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import team.project.yumarket.model.entity.home.HomeItem;
import team.project.yumarket.model.entity.home.HomeItemReview;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/04
 */
public interface HomeItemReviewRepository extends JpaRepository<HomeItemReview, Long> {
    // HomeItem을 통해서 모든 리뷰를 뽑아오는 query method
    Page<HomeItemReview> findAllByHomeItem(Pageable pageable, HomeItem homeItem);
}
