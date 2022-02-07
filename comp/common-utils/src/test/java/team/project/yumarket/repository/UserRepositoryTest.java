package team.project.yumarket.repository;

import org.aspectj.weaver.patterns.PatternNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.project.yumarket.model.entity.User;
import team.project.yumarket.model.enums.Role;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User createUser() {
        return User.builder()
                .password("test01")
                .name("홍길동")
                .nickname("쾌걸홍길동")
                .email("test01@naver.com")
                .role(Role.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("create : ")
    void create() {
        User user = createUser();

        User savedUser = userRepository.save(user);

        // Assertion
        Assertions.assertEquals(savedUser.getEmail(), "test01@naver.com");
    }

    @Test
    @DisplayName("read : ")
    @Transactional
    void read() {
        Optional<User> targetUser = userRepository.findById(1L);

        // Assertion
        Assertions.assertNotNull(targetUser);
        targetUser.ifPresent(user -> {
            Assertions.assertEquals(user.getId(), 1L);

            user.getMarketLikeList().forEach(like -> {
                System.out.println(like.getId());
            });
        });
    }

    @Test
    @DisplayName("update : ")
    @Transactional
    void update() {
        Optional<User> targetUser = userRepository.findById(1L);

        targetUser.ifPresent(user -> {
            user.setNickname("근육홍길동");
            User savedUser = userRepository.save(user);

            // Assertion
            Assertions.assertEquals(savedUser.getNickname(), "근육홍길동");

            user.getMarketReviewList().forEach(review -> {
                System.out.println(review.getContent());
            });
        });
    }

    @Test
    @DisplayName("delete : ")
    void delete() {
        userRepository.deleteById(1L);
    }
}