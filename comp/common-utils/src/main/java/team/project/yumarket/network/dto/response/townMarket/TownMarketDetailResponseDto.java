package team.project.yumarket.network.dto.response.townMarket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import team.project.yumarket.network.dto.response.HomeItemResponseDto;
import team.project.yumarket.network.dto.response.MarketLikeResponseDto;
import team.project.yumarket.network.dto.response.MarketReviewResponseDto;

import java.util.List;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/26
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TownMarketDetailResponseDto extends TownMarketResponseDto {

    @JsonProperty("item_list")
    private List<HomeItemResponseDto> homeItemResponseDtoList;

    @JsonProperty("review_list")
    private List<MarketReviewResponseDto> marketReviewResponseDtoList;

}
