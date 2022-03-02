package team.project.yumarket.model.entity.home;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
 * @since 2022/01/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Entity
public class MarketReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double grade;

    private String content;

    // MarketReview : TownMarket = N : 1
    @ManyToOne
    @JsonBackReference
    private TownMarket townMarket;

    // MarketReview : User = N : 1
    @ManyToOne
    @JsonBackReference
    private User user;
}
