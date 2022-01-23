package team.project.yumarket.ifs;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import team.project.yumarket.network.formats.CommunicationFormat;

import java.util.List;

/**
 * REST API의 CRUD에 관한 Interface
 *
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/14
 */
public interface CrudInterface<Req, Res> {

    public ResponseEntity<CommunicationFormat> create(CommunicationFormat<Req> request);

    public ResponseEntity<CommunicationFormat<Res>> read(Long id);

    public ResponseEntity<CommunicationFormat<Res>> update(CommunicationFormat<Req> request, Long id);

    public ResponseEntity<CommunicationFormat> delete(Long id);

    public ResponseEntity<CommunicationFormat<List<Res>>> search(
            @PageableDefault(sort = "id", size = 15, direction = Sort.Direction.ASC) Pageable pageable
    );
}
