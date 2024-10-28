package net.engineeringdigest.journalApp.controller.external_apis;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api.response.WeatherAPIResponse;
import net.engineeringdigest.journalApp.services.external_apis_service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@Slf4j
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/get-weather")
    public ResponseEntity<?> getWeatherDetails() {

        ResponseEntity<WeatherAPIResponse> responseEntity = weatherService.getWeatherOfCity("chennai");

        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            WeatherAPIResponse.Current current = responseEntity.getBody().getCurrent();
            log.info("Weather response: {}", current);
            return ResponseEntity.ok(current);
        }

        log.error("Failed to retrieve weather data, status: {}", responseEntity.getStatusCode());
        return ResponseEntity.status(responseEntity.getStatusCode()).body("Failed to retrieve weather data");
    }
}

