package team.project.yumarket.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import team.project.yumarket.model.entity.base.BaseEntity;
import team.project.yumarket.model.entity.home.HomeItemLike;
import team.project.yumarket.model.entity.home.HomeItemReview;
import team.project.yumarket.model.entity.home.MarketLike;
import team.project.yumarket.model.entity.home.MarketReview;
import team.project.yumarket.model.enums.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    private String email;

    private String password;

    private String name;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role; // 사용자의 권한

    @JsonProperty("password_updated_at")
    private LocalDateTime passwordUpdatedAt;

    @JsonProperty("last_login_at")
    private LocalDateTime lastLoginAt;

    @JsonProperty("unregistered_at")
    private String unregisteredAt;

    // User : MarketLike = 1 : N
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MarketLike> marketLikeList;

    // User : MarketReview = 1 : N
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MarketReview> marketReviewList;

    // User : HomeItemLike = 1 : N
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<HomeItemLike> homeItemLikeList;

    // User : HomeItemReview = 1 : N
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<HomeItemReview> homeItemReviewList;
}
