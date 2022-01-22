package team.project.yumarket.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import team.project.yumarket.model.entity.base.BaseEntity;
import team.project.yumarket.model.enums.Role;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * User에 대한 entity를 정의한 class
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String account;

    private String password;

    private String name;

    private String nickname;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role; // 사용자의 권한

    @JsonProperty("password_updated_at")
    private LocalDateTime passwordUpdatedAt;

    @JsonProperty("last_logined_at")
    private LocalDateTime lastLoginedAt;

    @JsonProperty("registered_at")
    private LocalDateTime registeredAt;

    @JsonProperty("unregistered_at")
    private String unregisteredAt;

    // TODO to Response DTO
}
