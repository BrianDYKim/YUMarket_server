package team.project.yumarket.model.entity.home;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import team.project.yumarket.model.entity.User;
import team.project.yumarket.model.entity.base.BaseEntity;

import javax.persistence.*;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/02/04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Entity
public class HomeItemLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // HomeItemLike : User = N : 1
    @ManyToOne
    @JsonBackReference
    private User user;

    // HomeItemLike : HomeItem = N : 1
    @ManyToOne
    @JsonBackReference
    private HomeItem homeItem;
}
