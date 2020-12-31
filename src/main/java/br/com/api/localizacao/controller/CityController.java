package br.com.api.localizacao.controller;

import br.com.api.localizacao.model.City;
import br.com.api.localizacao.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api
@RestController
@RequestMapping(value = "/cities")
public class CityController {

    @Autowired
    private CityService cityService;


    @ApiOperation("Api para incluir uma nova cidade")
    @PostMapping
    public ResponseEntity<City> save(@RequestBody City city) {
        return cityService.save(city);
    }

    @ApiOperation("Api para listar todas as cidades cadastradas")
    @GetMapping
    public Page<City> getAll() {
        return cityService.findAll();
    }

    @ApiOperation("Api para buscar todas as cidades cadastradas por nome")
    @GetMapping("/findByName")
    public Optional<City> findByName(
            @RequestParam("nameCity") String nameCity) {
        return cityService.findByName(nameCity);
    }

    @ApiOperation("Api para buscar todas as cidades cadastradas por código do cep")
    @GetMapping("/findByCodeCep")
    public City findByCodeCep(
            @RequestParam("codeCep") String codeCep) {
        return cityService.findByCep(codeCep);
    }

    @ApiOperation("Api para buscar cidade por código do ibge")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<City>> getById(@PathVariable Long id) {
        return cityService.findById(id);
    }

    @ApiOperation("Api para excluir cidade por código do ibge")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Optional<City>> deleteById(@PathVariable Long id) {
        return cityService.deleteById(id);

    }

    @ApiOperation("Api para atualizar a cidade por código do ibge")
    @PutMapping(path = "/{id}")
    public ResponseEntity<City> update(@PathVariable Long id, @RequestBody City newCity) {
        return cityService.update(id, newCity);
    }
}
