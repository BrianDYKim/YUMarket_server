package team.project.yumarket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import team.project.yumarket.ifs.CrudInterface;
import team.project.yumarket.network.formats.CommunicationFormat;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Doyeop Kim
 * @version 0.0
 * @since 2022/01/14
 */
public class BaseController<Req, Res> implements CrudInterface<Req, Res> {

    protected CrudInterface<Req, Res> baseService;

    @Override
    @PostMapping("")
    public ResponseEntity<CommunicationFormat> create(@Valid @RequestBody CommunicationFormat<Req> request) {
        return baseService.create(request);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CommunicationFormat<Res>> read(@PathVariable Long id) {
        return baseService.read(id);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CommunicationFormat<Res>> update(@Valid @RequestBody CommunicationFormat<Req> request, @PathVariable Long id)
            throws HttpRequestMethodNotSupportedException {
        return baseService.update(request, id);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<CommunicationFormat> delete(@PathVariable Long id) {
        return baseService.delete(id);
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<CommunicationFormat<List<Res>>> search(Pageable pageable) throws HttpRequestMethodNotSupportedException {
        return baseService.search(pageable);
    }
}
