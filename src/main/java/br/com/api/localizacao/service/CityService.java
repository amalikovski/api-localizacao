package br.com.api.localizacao.service;

import br.com.api.localizacao.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    public Page<City> findByName(String nameCity, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC,"nameCity");
        return cityRepository.findByName(nameCity.toLowerCase(), pageRequest);
    }

    public Page<City> findByCep(String codeCep, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC,"nameCity");
        return cityRepository.findByCep(codeCep, pageRequest);
    }

    public Page<City> findAll() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "nameCity");
        return new PageImpl<>(
                cityRepository.findAll(),
                pageRequest, size);
    }

    public ResponseEntity<City> save(City city) {
        cityRepository.save(city);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    public ResponseEntity<Optional<City>> findById(Long id) {
        Optional<City> city;
        try {
            city = cityRepository.findById(id);
            return new ResponseEntity<>(city, HttpStatus.OK);
        } catch (NoSuchElementException elemException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Optional<City>> deleteById(Long id) {
        try {
            cityRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException elemException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<City> update(Long id, City newCity) {
        return cityRepository.findById(id)
                .map(city -> {
                    city.setIdIbgeCity(newCity.getIdIbgeCity());
                    city.setNameCity(newCity.getNameCity());
                    City cityUpdated = cityRepository.save(city);
                    return ResponseEntity.ok().body(cityUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }

}
