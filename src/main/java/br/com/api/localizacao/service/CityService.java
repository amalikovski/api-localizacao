package br.com.api.localizacao.service;

import br.com.api.localizacao.model.Cep;
import br.com.api.localizacao.model.City;
import br.com.api.localizacao.model.Country;
import br.com.api.localizacao.model.State;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CONTINUE;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    @Inject
    CepService cepService;

    @Inject
    StateService stateService;

    @Autowired
    private RestTemplate prepararRestTemplate;

    public Optional<City> findByName(String nameCity) {
        return cityRepository.findByNameCityIgnoreCase(nameCity);
    }

    public City findByCep(String codeCep) {
        City city ;
        Optional<Cep> optionalCep = cepService.findByCodeCep(codeCep);

        if (optionalCep.isPresent()) {
            Cep cep = optionalCep.get();
            if (cep.getCity() != null) {
                Optional<City> optionalCity = cityRepository.findById(cep.getCity().getIdIbgeCity());
                if (optionalCity.isPresent()) {
                    city = optionalCity.get();
                } else {
                    city = findAndUpdateCep(cep, codeCep);
                }
            } else {
                city = findAndUpdateCep(cep, codeCep);
            }
        } else {
            city = findCityAndRegisterNewCep(codeCep);
        }
        return city;
    }

    public Page<City> findAll() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "nameCity");
        return new PageImpl<>(
                cityRepository.findAll(),
                pageRequest, size);
    }

    public ResponseEntity<City> save(City city) {
        cityRepository.save(city);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    public ResponseEntity<Optional<City>> findById(Long id) {
        Optional<City> city;
        try {
            city = cityRepository.findById(id);
            return new ResponseEntity<>(city, HttpStatus.OK);
        } catch (NoSuchElementException elemException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Optional<City>> deleteById(Long id) {
        try {
            cityRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException elemException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<City> update(Long id, City newCity) {
        return cityRepository.findById(id)
                .map(city -> {
                    city.setIdIbgeCity(newCity.getIdIbgeCity());
                    city.setNameCity(newCity.getNameCity());
                    City cityUpdated = cityRepository.save(city);
                    return ResponseEntity.ok().body(cityUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }

    private City findCityAndRegisterNewCep(String codeCep) {
        String codeCepTratado = codeCep.replaceAll("-", "");
        City city = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> res = new ResponseEntity<>(CONTINUE);
        try {
            res = prepararRestTemplate.exchange("https://viacep.com.br/ws/" + codeCepTratado + "/json/", HttpMethod.GET, entity, String.class);
        } catch (HttpStatusCodeException exception) {
            res = ResponseEntity.status(exception.getRawStatusCode()).headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
            if (res.getStatusCode().is4xxClientError()) {
                System.out.println("Client error utilizando API ViaCep" + res);
            } else if (res.getStatusCode().is5xxServerError()) {
                System.out.println("Server error utilizando API ViaCep" + res);
            }
        } catch (Exception exception) {
            System.out.println("Exception " + exception);
        }
        if (res.getStatusCode().is2xxSuccessful()) {
            city = registerNewCep(res);
        }
        return city;
    }

    private City findAndUpdateCep(Cep cep, String codeCep) {
        String codeCepTratado = codeCep.replaceAll("-", "");
        City city = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> res = new ResponseEntity<>(CONTINUE);
        try {
            res = prepararRestTemplate.exchange("https://viacep.com.br/ws/" + codeCepTratado + "/json/", HttpMethod.GET, entity, String.class);
        } catch (HttpStatusCodeException exception) {
            res = ResponseEntity.status(exception.getRawStatusCode()).headers(exception.getResponseHeaders())
                    .body(exception.getResponseBodyAsString());
            if (res.getStatusCode().is4xxClientError()) {
                System.out.println("Client error utilizando API ViaCep" + res);
            } else if (res.getStatusCode().is5xxServerError()) {
                System.out.println("Server error utilizando API ViaCep" + res);
            }
        } catch (Exception exception) {
            System.out.println("Exception " + exception);
        }
        if (res.getStatusCode().is2xxSuccessful()) {
            try {
                JSONObject newCep = new JSONObject(res.getBody());
                City cityCep = findAndRegisterNewCity(newCep);
                cepService.update(cep.getIdCep(), Cep.builder()
                        .codeCep(newCep.getString("cep"))
                        .logCep(newCep.getString("logradouro"))
                        .compCep(newCep.getString("complemento"))
                        .districtCep(newCep.getString("bairro"))
                        .nameCity(newCep.getString("localidade"))
                        .ufState(newCep.getString("uf"))
                        .giaCep(newCep.getString("gia"))
                        .dddCep(newCep.getString("ddd"))
                        .siafiCep(newCep.getString("siafi"))
                        .city(cityCep).build());
                Optional<City> cityRegistered = cityRepository.findById(cityCep.getIdIbgeCity());
                if(cityRegistered.isPresent()) {
                    city = cityRegistered.get();
                }
            } catch (JSONException e) {
                System.out.println("JSONException " + e);
            }
        }
        return city;
    }

    private City registerNewCep(ResponseEntity<String> res) {
        City cityCep = new City();
        try {
            JSONObject newCep = new JSONObject(res.getBody());
            cityCep = findAndRegisterNewCity(newCep);
            cepService.save(Cep.builder()
                    .codeCep(newCep.getString("cep"))
                    .logCep(newCep.getString("logradouro"))
                    .compCep(newCep.getString("complemento"))
                    .districtCep(newCep.getString("bairro"))
                    .nameCity(newCep.getString("localidade"))
                    .ufState(newCep.getString("uf"))
                    .giaCep(newCep.getString("gia"))
                    .dddCep(newCep.getString("ddd"))
                    .siafiCep(newCep.getString("siafi"))
                    .city(cityCep).build());
        } catch (JSONException e) {
            System.out.println("JSONException " + e);
        }
        return cityCep;
    }

    private City findAndRegisterNewCity(JSONObject newCep) {
        City cityCep = new City();
        try {
            String ufState = newCep.getString("uf");
            Optional<City> cityRegistered = findById(newCep.getLong("ibge")).getBody();
            if (cityRegistered.isPresent()) {
                cityCep = cityRegistered.get();
            } else {
                Optional<State> state = stateService.findByUf(ufState);
                if (state.isEmpty()) {
                    Long idIbgeState = Long.valueOf(String.valueOf(newCep.getLong("ibge")).substring(0, 2));
                    stateService.save(State.builder().idIbgeState(idIbgeState).ufState(ufState).country(Country.builder().idIbgeCountry(1058L).build()).build());
                    state = stateService.findByUf(ufState);
                }
                if(state.isPresent()) {
                    cityCep = City.builder().idIbgeCity(newCep.getLong("ibge")).nameCity(newCep.getString("localidade")).state(state.get()).build();
                    cityRepository.save(cityCep);
                }
            }
        } catch (JSONException e) {
            System.out.println("JSONException findAndRegisterNewCity() " + e);
        }
        return cityCep;
    }

}
