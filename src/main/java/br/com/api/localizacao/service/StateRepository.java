package br.com.api.localizacao.service;

import br.com.api.localizacao.model.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    @Query("FROM State c " +
            "WHERE LOWER(c.nameState) like %:nameState% ")
    Page<State> findByName(@Param("nameState") String nameState,
                             Pageable pageable);
}
