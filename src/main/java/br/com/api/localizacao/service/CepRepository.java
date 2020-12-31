package br.com.api.localizacao.service;

import br.com.api.localizacao.model.Cep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CepRepository extends JpaRepository<Cep, Long> {

    Optional<Cep> findByCodeCep(String codeCep);

}
