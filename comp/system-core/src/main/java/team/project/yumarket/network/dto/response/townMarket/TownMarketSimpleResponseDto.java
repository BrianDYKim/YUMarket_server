package team.project.yumarket.network.dto.response.townMarket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HomeItem의 detail 응답에 사용되는 TownMarket에 대한 simple response dto
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/07
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TownMarketSimpleResponseDto {

    private Long id;

    private String name;

    private Double latitude;

    private Double longitude;
}
