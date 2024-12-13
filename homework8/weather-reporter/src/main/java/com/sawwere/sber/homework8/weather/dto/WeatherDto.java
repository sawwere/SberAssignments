package com.sawwere.sber.homework8.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto {
    @JsonProperty(value = "temp_c")
    private Double temperatureCelsius;
    @JsonProperty(value = "cloud", namespace = "current")
    private Double cloud;

    @Override
    public String toString() {
        return "WeatherDto{" +
                "temperatureCelsius=" + temperatureCelsius +
                ", cloud=" + cloud +
                '}';
    }
}
