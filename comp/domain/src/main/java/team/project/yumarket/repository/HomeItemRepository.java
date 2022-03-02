package team.project.yumarket.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.project.yumarket.model.entity.home.HomeItem;
import team.project.yumarket.model.enums.home.Category;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/19
 */
@Repository
public interface HomeItemRepository extends JpaRepository<HomeItem, Long> {

    // Category별로 HomeItem의 Page를 리턴해주는 메소드
    Page<HomeItem> findAllByCategory(Pageable pageable, Category category);
}
