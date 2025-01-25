package com.sawwere.sber.homework15.service;

import com.sawwere.sber.homework15.exception.ApiClientSideException;
import com.sawwere.sber.homework15.exception.ApiServerSideException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

/**
 * Отвечает за общение с API сервисом погоды, обработку полученных данных.
 * <p>
 * Данная реализация основана на использовании {@link RestClient} для выполнения запросов к стороннему Api.
 */
@Service
public class WeatherApiServiceImpl implements WeatherApiService {
    private static final String BASE_URL = "https://api.weatherapi.com/v1/?key={key}";

    private final RestClient restClient;

    public WeatherApiServiceImpl(@Value("${app.api.weatherApiKey}") String apiKey) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(500);
        factory.setReadTimeout(1500);

        restClient = RestClient.builder()
                .defaultHeader(HttpHeaders.ACCEPT, String.valueOf(MediaType.APPLICATION_JSON))
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> {
                    HttpStatusCode statusCode = response.getStatusCode();
                    throw new ApiClientSideException(statusCode);
                })
                .defaultStatusHandler(HttpStatusCode::is5xxServerError,  (request, response) -> {
                    throw new ApiServerSideException(response.getStatusCode());
                })
                .defaultUriVariables(Map.of("key", apiKey))
                .baseUrl(BASE_URL)
                .requestFactory(factory)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    public String getWeather(String query) {
        var res =  restClient.get()
                .uri("/current.json?q={q}", Map.of("q", query))
                .retrieve()
                .toEntity(String.class);
        return res.getBody();
    }
}
