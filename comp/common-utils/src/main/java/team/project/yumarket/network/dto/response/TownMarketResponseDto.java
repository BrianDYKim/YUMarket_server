package team.project.yumarket.network.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TownMarketResponseDto {

    private Long id;

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

    @JsonProperty("items")
    private List<HomeItemResponseDto> homeItemResponseDtoList;
}
