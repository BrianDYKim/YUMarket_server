package team.project.yumarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.project.yumarket.model.entity.User;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/14
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // TODO user에 대한 정보를 제공하는 Query method 작성
}
