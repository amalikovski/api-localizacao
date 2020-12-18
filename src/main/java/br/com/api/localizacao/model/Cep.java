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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long idCep;

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
    @ManyToOne
    @JoinColumn(name = "id_ibge_city")
    private City city;

}
