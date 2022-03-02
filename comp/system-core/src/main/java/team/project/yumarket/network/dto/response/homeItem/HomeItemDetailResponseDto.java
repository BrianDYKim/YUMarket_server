package team.project.yumarket.network.dto.response.homeItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import team.project.yumarket.network.dto.response.townMarket.TownMarketSimpleResponseDto;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class HomeItemDetailResponseDto extends HomeItemResponseDto {

    @JsonProperty("market_info")
    private TownMarketSimpleResponseDto marketInfo;
}
