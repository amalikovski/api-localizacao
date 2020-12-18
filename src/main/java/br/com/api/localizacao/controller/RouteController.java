package br.com.api.localizacao.controller;

import br.com.api.localizacao.model.Cep;
import br.com.api.localizacao.model.City;
import br.com.api.localizacao.service.RouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(value = "/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;


    @ApiOperation("Api para cálculo de rotas através de uma listagem de ceps")
    @GetMapping(value = "/findRoute")
    public ResponseEntity<List<City>> findRoute(@RequestBody List<Cep> ceps) {
        return routeService.findRoute(ceps);
    }


}
