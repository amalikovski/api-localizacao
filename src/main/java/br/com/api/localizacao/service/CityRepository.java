package br.com.api.localizacao.service;

import br.com.api.localizacao.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByNameCityIgnoreCase(String nameCity);

    @Query("FROM City c, Cep ce " +
            "WHERE ce.codeCep = :codeCep AND ce.codeCep <> null")
    City findByCep(@Param("codeCep") String codeCep);

}
