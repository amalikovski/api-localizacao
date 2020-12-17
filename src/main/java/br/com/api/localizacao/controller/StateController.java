package br.com.api.localizacao.controller;

import br.com.api.localizacao.model.State;
import br.com.api.localizacao.service.StateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api
@RestController
@RequestMapping(value = "/states")
public class StateController {

    @Autowired
    private StateService stateService;

    @ApiOperation("Api para incluir novo estado")
    @PostMapping
    public ResponseEntity<State> save(@RequestBody State state) {
        return stateService.save(state);
    }

    @ApiOperation("Api para listar todos os estados cadastrados")
    @GetMapping
    public Page<State> getAll() {
        return stateService.findAll();
    }

    @ApiOperation("Api para buscar todos os estados cadastradas por nome")
    @GetMapping("/findByName")
    public Page<State> findByName(
            @RequestParam("nameState") String nameState,
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size) {
        return stateService.findByName(nameState, page, size);
    }

    @ApiOperation("Api para busca de estado pelo código do ibge")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<State>> getById(@PathVariable Long id) {
        return stateService.findById(id);
    }

    @ApiOperation("Api para exclusão de estado pelo código do ibge")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Optional<State>> deleteById(@PathVariable Long id) {
        return stateService.deleteById(id);
    }

    @ApiOperation("Api para alteração de estado pelo código do ibge")
    @PutMapping(path = "/{id}")
    public ResponseEntity<State> update(@PathVariable Long id, @RequestBody State newState) {
        return stateService.update(id, newState);
    }
}
