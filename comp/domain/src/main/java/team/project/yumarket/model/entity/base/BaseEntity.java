package team.project.yumarket.model.entity.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 모든 entity에서 상속받아야하는 class
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/19
 */

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @JsonProperty("created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
