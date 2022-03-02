package team.project.yumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.project.yumarket.model.entity.home.TownMarket;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/17
 */
@Repository
public interface TownMarketRepository extends JpaRepository<TownMarket, Long> {


}
