package br.com.api.localizacao.service;

import br.com.api.localizacao.model.Cep;
import br.com.api.localizacao.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Inject
    CityService cityService;

    public ResponseEntity<List<City>> findRoute(List<Cep> ceps) {
        List<City> cities = new ArrayList<>();
        if(CollectionUtils.isEmpty(ceps)) {
            return new ResponseEntity<List<City>>(HttpStatus.BAD_REQUEST);
        } else {
            City oldCity = new City();
            for(Cep cep : ceps){
                if (!oldCity.equals(cityService.findByCep(cep.getCodeCep()))) {
                    cities.add(cityService.findByCep(cep.getCodeCep()));
                }
                oldCity = cityService.findByCep(cep.getCodeCep());
            }
            return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
        }

    }

}
