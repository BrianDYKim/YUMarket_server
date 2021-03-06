package team.project.yumarket.network.dto.response.homeItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import team.project.yumarket.model.enums.home.Category;
import team.project.yumarket.model.enums.home.DetailCategory;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class HomeItemResponseDto {

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
    private Integer originalPrice;

    @JsonProperty("sale_price")
    private Integer salePrice;

    @JsonProperty("stock_quantity")
    private Integer stockQuantity;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("sale_updated_at")
    private LocalDateTime saleUpdatedAt;

    @JsonProperty("town_market_id")
    private Long townMarketId;

    // 2022/02/04 추가 속성 (likeQuantity, reviewQuantity)
    @JsonProperty("like_quantity")
    private Integer likeQuantity;

    @JsonProperty("review_quantity")
    private Integer reviewQuantity;
}
