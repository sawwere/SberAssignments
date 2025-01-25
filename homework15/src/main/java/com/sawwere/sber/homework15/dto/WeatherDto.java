package com.sawwere.sber.homework15.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * DTO, который содержит требуемые в условиях задачи поля:
 * <li>Температура по Цельсию</li>
 * <li>Облачность</li>
 */
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
