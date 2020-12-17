package br.com.api.localizacao.service;

import br.com.api.localizacao.model.Cep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CepRepository extends JpaRepository<Cep, Long> {

    @Query("FROM Cep c " +
            "WHERE c.codeCep = :codeCep ")
    Page<Cep> findByCodeCep(@Param("codeCep") String codeCep,
            Pageable pageable);


}
