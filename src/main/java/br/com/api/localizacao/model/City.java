package br.com.api.localizacao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "city")
public class City {

    @Id
    private Long idIbgeCity;

    private String nameCity;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ibge_state")
    private State state;

    @JsonManagedReference
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private Set<Cep> ceps;

}
