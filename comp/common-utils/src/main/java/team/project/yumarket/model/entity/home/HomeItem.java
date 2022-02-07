package team.project.yumarket.model.entity.home;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import team.project.yumarket.model.entity.base.BaseEntity;
import team.project.yumarket.model.enums.home.Category;
import team.project.yumarket.model.enums.home.DetailCategory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/19
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Entity
public class HomeItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    @JsonProperty("detail_category")
    private DetailCategory detailCategory;

    @JsonProperty("item_image_url")
    private String itemImageUrl;

    private String name;

    @JsonProperty("original_price")
    private int originalPrice;

    @JsonProperty("sale_price")
    private int salePrice;

    @JsonProperty("stock_quantity")
    private int stockQuantity;

    @JsonProperty("sale_updated_at")
    private LocalDateTime saleUpdatedAt;

    // TODO TownMarket, like, ItemReview와 연관관계 매핑
    // HomeItem : TownMarket = N : 1
    @ManyToOne
    @JsonBackReference
    private TownMarket townMarket;

    // HomeItem : HomeItemLike = 1 : N
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "homeItem", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<HomeItemLike> homeItemLikeList;

    // HomeItem : HomeItemReview = 1 : N
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "homeItem", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<HomeItemReview> homeItemReviewList;
}
