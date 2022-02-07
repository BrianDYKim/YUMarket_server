package team.project.yumarket.network.dto.response.townMarket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TownMarketResponseDto {

    private Long id;

    private String name;

    @JsonProperty("is_open")
    private boolean isOpen;

    @JsonProperty("open_time")
    private LocalTime openTime;

    @JsonProperty("close_time")
    private LocalTime closeTime;

    private String address;

    private Double latitude;

    private Double longitude;

    @JsonProperty("market_image_url")
    private String marketImageUrl;

    @JsonProperty("item_quantity")
    private int itemQuantity;

    @JsonProperty("like_quantity")
    private int likeQuantity;

    @JsonProperty("review_quantity")
    private int reviewQuantity;
}
