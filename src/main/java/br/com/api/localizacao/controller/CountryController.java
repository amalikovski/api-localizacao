package br.com.api.localizacao.controller;

import br.com.api.localizacao.model.Country;
import br.com.api.localizacao.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api
@RestController
@RequestMapping(value = "/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @ApiOperation("Api para incluir novo país")
    @PostMapping
    public ResponseEntity<Country> save(@RequestBody Country country) {
        return countryService.save(country);
    }

    @ApiOperation("Api para listar todos os paises cadastrados")
    @GetMapping
    public Page<Country> getAll() {
        return countryService.findAll();
    }

    @ApiOperation("Api para buscar todos os países cadastradas por nome")
    @GetMapping("/findByName")
    public Page<Country> findByName(
            @RequestParam("nameCountry") String nameCountry,
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size) {
        return countryService.findByName(nameCountry, page, size);
    }

    @ApiOperation("Api para busca de pais pelo código do ibge")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<Country>> getById(@PathVariable Long id) {
        return countryService.findById(id);
    }

    @ApiOperation("Api para exclusão de país pelo código do ibge")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Optional<Country>> deleteById(@PathVariable Long id) {
        return countryService.deleteById(id);
    }

    @ApiOperation("Api para alteração de país pelo código do ibge")
    @PutMapping(path = "/{id}")
    public ResponseEntity<Country> update(@PathVariable Long id, @RequestBody Country newCountry) {
        return countryService.update(id, newCountry);
    }
}
