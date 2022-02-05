package team.project.yumarket.network.dto.request.townMarket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.project.yumarket.model.entity.home.MarketLike;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/22
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketLikeRequestDto {

    @JsonProperty("town_market_id")
    private Long townMarketId;

    @JsonProperty("user_id")
    private Long userId;
}