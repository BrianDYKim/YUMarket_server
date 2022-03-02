package team.project.yumarket.component;

import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Entity Auditing의 주체를 결정해주는 component class
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/17
 */
@Component
public class AuditorAware implements org.springframework.data.domain.AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("AdminServer");
    }
}
