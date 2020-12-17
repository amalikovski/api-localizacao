package br.com.api.localizacao.service;

import br.com.api.localizacao.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("FROM City c " +
            "WHERE LOWER(c.nameCity) like %:nameCity% ")
    Page<City> findByName(@Param("nameCity") String nameCity,
                      Pageable pageable);

    @Query("FROM City c, Cep ce " +
            "WHERE ce.codeCep = :codeCep ")
    Page<City> findByCep(@Param("codeCep") String codeCep,
                      Pageable pageable);

}
