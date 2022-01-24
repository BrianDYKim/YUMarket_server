package team.project.yumarket.network.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/25
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketReviewResponseDto {

    private Long id;

    private double grade;

    private String content;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("town_market_id")
    private Long townMarketId;

    @JsonProperty("user_id")
    private Long userId;
}
