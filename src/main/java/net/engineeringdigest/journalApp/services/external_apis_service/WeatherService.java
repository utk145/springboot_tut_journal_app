package net.engineeringdigest.journalApp.services.external_apis_service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api.response.WeatherAPIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherService {

    private static final String apiAccessKey = "0ef65d46c33449b1b1e141638232205";
    private static final String baseURL = "http://api.weatherapi.com/v1";

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<WeatherAPIResponse> getWeatherOfCity(String cityName) {
        String endPointURL = String.format("%s/current.json?key=%s&q=%s", baseURL, apiAccessKey, cityName);

        try {
            return restTemplate.exchange(endPointURL, HttpMethod.GET, null, WeatherAPIResponse.class);
        } catch (Exception e) {
            log.error("Error fetching weather data: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

