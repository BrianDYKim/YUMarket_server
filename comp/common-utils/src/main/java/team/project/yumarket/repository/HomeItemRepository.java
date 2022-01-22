package team.project.yumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.project.yumarket.model.entity.home.HomeItem;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/19
 */
@Repository
public interface HomeItemRepository extends JpaRepository<HomeItem, Long> {


}
