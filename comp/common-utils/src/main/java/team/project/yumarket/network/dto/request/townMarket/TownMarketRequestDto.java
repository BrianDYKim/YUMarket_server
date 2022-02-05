package team.project.yumarket.network.dto.request.townMarket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.project.yumarket.model.entity.home.TownMarket;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TownMarketRequestDto {

    private String name;

    @JsonProperty("is_open")
    private Boolean isOpen;

    @JsonProperty("open_time")
    private LocalTime openTime;

    @JsonProperty("close_time")
    private LocalTime closeTime;

    private String address;

    private Double latitude;

    private Double longitude;

    @JsonProperty("market_image_url")
    private String marketImageUrl;
}
