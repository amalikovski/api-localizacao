package br.com.api.localizacao.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "country")
public class Country {

    @Id
    private Long idIbgeCountry;

    private String nameCountry;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<State> states;
}
