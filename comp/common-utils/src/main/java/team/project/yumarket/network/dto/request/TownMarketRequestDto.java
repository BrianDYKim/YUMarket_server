package team.project.yumarket.network.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.project.yumarket.model.entity.home.TownMarket;

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
public class TownMarketRequestDto {

    private String name;

    @JsonProperty("is_open")
    private boolean isOpen;

    @JsonProperty("open_time")
    private LocalDateTime openTime;

    @JsonProperty("close_time")
    private LocalDateTime closeTime;

    private String address;

    private Double latitude;

    private Double longitude;

    @JsonProperty("market_image_url")
    private String marketImageUrl;

    // request -> entity
    public TownMarket toEntity() {
        return TownMarket.builder()
                .name(this.name)
                .isOpen(this.isOpen)
                .openTime(this.openTime)
                .closeTime(this.closeTime)
                .address(this.address)
                .longitude(this.longitude)
                .latitude(this.latitude)
                .marketImageUrl(this.marketImageUrl)
                .build();
    }
}
