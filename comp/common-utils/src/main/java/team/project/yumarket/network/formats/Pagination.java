package team.project.yumarket.network.formats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/14
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {

    @JsonProperty("total_page")
    private Integer totalPage;

    @JsonProperty("total_elements")
    private Long totalElements;

    @JsonProperty("current_page")
    private Integer currentPage;

    @JsonProperty("current_elements")
    private Integer currentElements;
}
