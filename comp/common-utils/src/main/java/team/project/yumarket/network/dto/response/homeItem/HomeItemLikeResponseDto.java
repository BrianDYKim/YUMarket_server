package team.project.yumarket.network.dto.response.homeItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeItemLikeResponseDto {

    private Long id;

    @JsonProperty("home_item_id")
    private Long homeItemId;

    @JsonProperty("user_id")
    private Long userId;
}
