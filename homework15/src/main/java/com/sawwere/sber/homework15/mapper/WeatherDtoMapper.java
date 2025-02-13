package com.sawwere.sber.homework15.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sawwere.sber.homework15.dto.WeatherDto;
import org.springframework.stereotype.Component;

@Component
public class WeatherDtoMapper {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherDtoMapper() {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public WeatherDto mapToDto(String string) throws JsonProcessingException {
        var nodeCurrent = objectMapper.readTree(string).get("current");
        if (nodeCurrent == null) {
            return null;
        }

        return WeatherDto.builder()
                .temperatureCelsius(nodeCurrent.get("temp_c").asDouble())
                .cloud(nodeCurrent.get("cloud").asDouble())
                .build();
    }
}
