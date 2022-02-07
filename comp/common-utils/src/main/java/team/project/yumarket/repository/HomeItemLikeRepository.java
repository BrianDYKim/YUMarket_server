package team.project.yumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.project.yumarket.model.entity.home.HomeItemLike;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/04
 */
public interface HomeItemLikeRepository extends JpaRepository<HomeItemLike, Long> {

}
