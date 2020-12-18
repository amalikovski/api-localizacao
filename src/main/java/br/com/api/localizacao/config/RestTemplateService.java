package br.com.api.localizacao.config;

import org.springframework.web.client.RestTemplate;

public interface RestTemplateService {

	RestTemplate prepararRestTemplate();

}
