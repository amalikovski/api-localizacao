package br.com.api.localizacao.service;

import br.com.api.localizacao.model.Country;
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
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public Page<Country> findByName(String nameCountry, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC,"nameCountry");
        return countryRepository.findByName(nameCountry.toLowerCase(), pageRequest);
    }

    public Page<Country> findAll() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "nameCountry");
        return new PageImpl<>(
                countryRepository.findAll(),
                pageRequest, size);
    }

    public ResponseEntity<Country> save(Country country) {
        countryRepository.save(country);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    public ResponseEntity<Optional<Country>> findById(Long id) {
        Optional<Country> country;
        try {
            country = countryRepository.findById(id);
            return new ResponseEntity<>(country, HttpStatus.OK);
        } catch (NoSuchElementException elemException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Optional<Country>> deleteById(Long id) {
        try {
            countryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException elemException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Country> update(Long id, Country newCountry) {
        return countryRepository.findById(id)
                .map(country -> {
                    country.setIdIbgeCountry(newCountry.getIdIbgeCountry());
                    country.setNameCountry(newCountry.getNameCountry());
                    Country countryUpdated = countryRepository.save(country);
                    return ResponseEntity.ok().body(countryUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }

}
