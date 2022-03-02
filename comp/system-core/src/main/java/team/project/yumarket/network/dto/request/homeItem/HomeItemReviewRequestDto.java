package team.project.yumarket.network.dto.request.homeItem;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeItemReviewRequestDto {

    @Min(value = 0, message = "올바른 평점 범위가 아닙니다.(0 이상 5 이하)")
    @Max(value = 5, message = "올바른 평점 범위가 아닙니다.(0 이상 5 이하)") // 0.5부터 5까지 평점을 매길 수 있도록 구현
    private Double grade;

    @Size(max = 300, message = "리뷰는 300자로 제한이 되어있습니다")
    private String content;

    @JsonProperty("home_item_id")
    private Long homeItemId;

    @JsonProperty("user_id")
    private Long userId;
}
