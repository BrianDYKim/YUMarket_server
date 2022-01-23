package team.project.yumarket.model.entity.home;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import team.project.yumarket.model.entity.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * TownMarket에 대한 entity를 정의한 class
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Entity
public class TownMarket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonProperty("is_open")
    private boolean isOpen;

    @JsonProperty("open_time")
    private LocalDateTime openTime;

    @JsonProperty("close_time")
    private LocalDateTime closeTime;

    private String address;

    private Double latitude;

    private Double longitude;

    @JsonProperty("market_image_url")
    private String marketImageUrl;

    // TownMarket : HomeItem = 1 : N
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "townMarket", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<HomeItem> homeItemList;

    // TownMarket : MarketLike = 1 : N
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "townMarket", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MarketLike> marketLikeList;

    // TownMarket : MarketReview = 1 : N
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "townMarket", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MarketReview> marketReviewList;
}