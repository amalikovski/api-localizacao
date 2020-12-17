package br.com.api.localizacao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "cep")
public class Cep {

    @Id
    private Long idIbgeCep;

    private String codeCep;

    private String logCep;

    private String compCep;

    private String districtCep;

    private String nameCity;

    private String ufState;

    private String giaCep;

    private String dddCep;

    private String siafiCep;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ibge_city")
    private City city;

}
