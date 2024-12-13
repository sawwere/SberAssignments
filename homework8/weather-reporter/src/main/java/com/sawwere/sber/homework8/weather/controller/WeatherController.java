package com.sawwere.sber.homework8.weather.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sawwere.sber.homework8.weather.dto.WeatherDto;
import com.sawwere.sber.homework8.weather.mapper.WeatherDtoMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class WeatherController {
    private final OkHttpClient client = new OkHttpClient();
    private final WeatherDtoMapper weatherDtoMapper = new WeatherDtoMapper();

    private final String apiKey;
    public static final String GET_CURRENT_WEATHER = "https://api.weatherapi.com/v1/current.json?key=%s&q=%s";

    public WeatherController(String apiKey) {
        this.apiKey = apiKey;
    }

    public WeatherDto getCurrentWeather(String query) throws IOException {
        Request request = new Request.Builder()
                .url(GET_CURRENT_WEATHER.formatted(apiKey, query))
                .build();
        try (Response response = client.newCall(request).execute()) {
            var body = response.body();
            if (body == null) {
                throw new IOException("Can't access api service");
            }
            return weatherDtoMapper.mapToDto(body.string());

        }
    }
}
