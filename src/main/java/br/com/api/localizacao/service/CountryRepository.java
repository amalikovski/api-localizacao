package br.com.api.localizacao.service;

import br.com.api.localizacao.model.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("FROM Country c " +
            "WHERE LOWER(c.nameCountry) like %:nameCountry% ")
    Page<Country> findByName(@Param("nameCountry") String nameCountry,
                          Pageable pageable);
}
