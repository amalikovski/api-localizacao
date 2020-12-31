package br.com.api.localizacao.service;

import br.com.api.localizacao.model.Cep;
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
public class CepService {

    @Autowired
    CepRepository cepRepository;

    public Optional<Cep> findByCodeCep(String codeCep) {
        return cepRepository.findByCodeCep(codeCep);
    }

    public Page<Cep> findAll() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "codeCep");
        return new PageImpl<>(
                cepRepository.findAll(),
                pageRequest, size);
    }

    public ResponseEntity<Cep> save(Cep cep) {
        cepRepository.save(cep);
        return new ResponseEntity<>(cep, HttpStatus.OK);
    }


    public ResponseEntity<Optional<Cep>> findById(Long id) {
        Optional<Cep> cep;
        try {
            cep = cepRepository.findById(id);
            return new ResponseEntity<>(cep, HttpStatus.OK);
        } catch (NoSuchElementException elemException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<Optional<Cep>> deleteById(Long id) {
        try {
            cepRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException elemException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<Cep> update(Long id, Cep newCep) {
        return cepRepository.findById(id)
                .map(cep -> {
                    cep.setCodeCep(newCep.getCodeCep());
                    cep.setLogCep(newCep.getLogCep());
                    cep.setCompCep(newCep.getCompCep());
                    cep.setDistrictCep(newCep.getDistrictCep());
                    cep.setNameCity(newCep.getNameCity());
                    cep.setUfState(newCep.getUfState());
                    cep.setGiaCep(newCep.getGiaCep());
                    cep.setDddCep(newCep.getDddCep());
                    cep.setSiafiCep(newCep.getSiafiCep());
                    Cep cepUpdated = cepRepository.save(cep);
                    return ResponseEntity.ok().body(cepUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }

}
