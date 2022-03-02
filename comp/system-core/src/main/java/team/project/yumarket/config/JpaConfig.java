package team.project.yumarket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Jpa에 대한 정책을 관리하는 Config class
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/14
 */
@EnableJpaAuditing
@Configuration
public class JpaConfig {
}
