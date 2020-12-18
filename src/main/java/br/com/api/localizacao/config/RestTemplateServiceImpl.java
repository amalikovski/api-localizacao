package br.com.api.localizacao.config;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class RestTemplateServiceImpl implements RestTemplateService {


    @Override
    @Bean(name = "prepararRestTemplate")
    public RestTemplate prepararRestTemplate() {
        try {


            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setCookieSpec(CookieSpecs.STANDARD).build())
                    .build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            requestFactory.setConnectTimeout(35000); // 35 seconds
            requestFactory.setReadTimeout(35000); // 35 seconds
            return new RestTemplate(requestFactory);
        } catch (Exception e) {
            System.out.println("Exception Rest Template " + e);
        }
        return new RestTemplate();
    }
}
