package br.com.api.localizacao.service;

import br.com.api.localizacao.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    Optional<State> findByNameStateIgnoreCase(String nameState);

    Optional<State> findByUfStateIgnoreCase(String ufState);
}
