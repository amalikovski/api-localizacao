package br.com.api.localizacao.service;

import br.com.api.localizacao.model.State;
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
public class StateService {

    @Autowired
    StateRepository stateRepository;

    public Page<State> findByName(String nameState, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC,"nameState");
        return stateRepository.findByName(nameState.toLowerCase(), pageRequest);
    }

    public Page<State> findAll() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "nameState");
        return new PageImpl<>(
                stateRepository.findAll(),
                pageRequest, size);
    }

    public ResponseEntity<State> save(State State) {
        stateRepository.save(State);
        return new ResponseEntity<>(State, HttpStatus.OK);
    }

    public ResponseEntity<Optional<State>> findById(Long id) {
        Optional<State> State;
        try {
            State = stateRepository.findById(id);
            return new ResponseEntity<>(State, HttpStatus.OK);
        } catch (NoSuchElementException elemException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Optional<State>> deleteById(Long id) {
        try {
            stateRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException elemException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<State> update(Long id, State newState) {
        return stateRepository.findById(id)
                .map(state -> {
                    state.setIdIbgeState(newState.getIdIbgeState());
                    state.setNameState(newState.getNameState());
                    State stateUpdated = stateRepository.save(state);
                    return ResponseEntity.ok().body(stateUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }

}
