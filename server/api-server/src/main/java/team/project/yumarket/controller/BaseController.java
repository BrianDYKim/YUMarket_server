package team.project.yumarket.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.project.yumarket.ifs.CrudInterface;
import team.project.yumarket.network.formats.CommunicationFormat;

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
    public ResponseEntity<CommunicationFormat<Res>> create(@RequestBody CommunicationFormat<Req> request) {
        return baseService.create(request);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CommunicationFormat<Res>> read(@PathVariable Long id) {
        return baseService.read(id);
    }

    @Override
    @PutMapping("")
    public ResponseEntity<CommunicationFormat<Res>> update(@RequestBody CommunicationFormat<Req> request) {
        return baseService.update(request);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<CommunicationFormat<Res>> delete(@PathVariable Long id) {
        return baseService.delete(id);
    }

    @Override
    @GetMapping("/list")
    public ResponseEntity<CommunicationFormat<List<Res>>> search(Pageable pageable) {
        return baseService.search(pageable);
    }
}
