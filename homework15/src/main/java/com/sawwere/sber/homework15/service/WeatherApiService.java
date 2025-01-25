package com.sawwere.sber.homework15.service;

/**
 * Отвечает за общение с API сервисом погоды, обработку полученных данных.
 */
public interface WeatherApiService {
    /**
     * Выполняет запрос к сервису weatherApi на получение текущей погоды в заданной локации
     * @param query название города или локации
     * @return ответа сервиса в виде строки
     */
    String getWeather(String query);
}
