package com.sawwere.sber.homework15.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sawwere.sber.homework15.dto.WeatherDto;
import com.sawwere.sber.homework15.mapper.WeatherDtoMapper;
import com.sawwere.sber.homework15.service.WeatherApiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Контроллер для обработки запросов к сервису погоды
 */
@RestController
@RequiredArgsConstructor
public class WeatherController {
    public static final String GET_CURRENT_WEATHER = "/api/weather";

    private final WeatherApiServiceImpl weatherApiService;
    private final WeatherDtoMapper weatherDtoMapper;


    /**
     * Обрабатывает запрос на получение текущей погоды в заданной локации
     * @param query название города или локации
     * @return {@link WeatherDto} в случае успешного выполнения запроса или сообщение об ошибке в противном случае
     */
    @GetMapping(GET_CURRENT_WEATHER)
    public ResponseEntity<?> getCurrentWeather(@RequestParam(name = "query") String query) {
        try {
            return ResponseEntity.ofNullable(weatherDtoMapper.mapToDto(weatherApiService.getWeather(query)));
        }
        catch (RuntimeException | JsonProcessingException ex) {
            return ResponseEntity.ofNullable(
                    Map.of("error", ex.getMessage())
            );
        }
    }
}
