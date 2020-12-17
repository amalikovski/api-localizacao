package br.com.api.localizacao.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "state")
public class State {

    @Id
    private Long idIbgeState;

    private String nameState;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    private Set<City> cities;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ibge_country")
    private Country country;

}
