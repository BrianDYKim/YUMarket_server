package team.project.yumarket.network.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Builder
public class HomeItemResponseDto {

    private Long id;

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

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("sale_updated_at")
    private LocalDateTime saleUpdatedAt;

    @JsonProperty("town_market_id")
    private Long townMarketId;
}
