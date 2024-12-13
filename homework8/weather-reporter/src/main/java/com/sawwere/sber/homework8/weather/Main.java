package com.sawwere.sber.homework8.weather;

import com.sawwere.sber.homework8.weather.controller.WeatherController;

import java.io.*;
import java.util.Scanner;

public class Main {
    private static String loadApiKey() throws IOException {
        // в демонстрационных целях оставил апи ключ в открытом доступе
        File file = new File("./weather-reporter/src/main/resources/apiToken.txt");
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.readLine();
        }
    }

    public static void main(String[] args) throws IOException {
        String apiKey = loadApiKey();
        WeatherController weatherController = new WeatherController(apiKey);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter query or 0 for exit: ");
            var input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }
            var result = weatherController.getCurrentWeather(input);
            if (result == null) {
                System.out.println("Requested location was not found.");
            } else {
                System.out.println(
                        "In the requested city, the temperature is %.2f degrees Celsius and the cloud cover is %.2f."
                                .formatted(result.getTemperatureCelsius(), result.getCloud())
                );
            }
        }
    }
}