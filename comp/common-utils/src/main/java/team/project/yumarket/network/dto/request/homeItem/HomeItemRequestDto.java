package team.project.yumarket.network.dto.request.homeItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.project.yumarket.model.enums.home.DetailCategory;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeItemRequestDto {

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

    @JsonProperty("town_market_id")
    private Long townMarketId;
}
