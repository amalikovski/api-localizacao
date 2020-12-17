package br.com.api.localizacao.controller;

import br.com.api.localizacao.model.Cep;
import br.com.api.localizacao.service.CepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api
@RestController
@RequestMapping(value = "/ceps")
public class CepController {

    @Autowired
    private CepService cepService;


    @ApiOperation("Api para incluir novo Cep")
    @PostMapping
    public ResponseEntity<Cep> save(@RequestBody Cep city) {
        return cepService.save(city);
    }

    @ApiOperation("Api para listar todos os ceps cadastrados")
    @GetMapping
    public Page<Cep> getAll() {
        return cepService.findAll();

    }

    @ApiOperation("Api para busca de Cep por código")
    @GetMapping("/findByCodeCep")
    public Page<Cep> findByCodeCep(
            @RequestParam("codeCep") String codeCep,
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size) {
        return cepService.findByCodeCep(codeCep, page, size);
    }

    @ApiOperation("Api para buscar um Cep pelo Id")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<Cep>> findById(@PathVariable Long id) {
        return cepService.findById(id);
    }

    @ApiOperation("Api para excluir um Cep pelo Id")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Optional<Cep>> deleteById(@PathVariable Long id) {
        return cepService.deleteById(id);
    }

    @ApiOperation("Api para atualizar um Cep pelo Id")
    @PutMapping(path = "/{id}")
    public ResponseEntity<Cep> update(@PathVariable Long id, @RequestBody Cep newCep) {
        return cepService.update(id, newCep);
    }
}
