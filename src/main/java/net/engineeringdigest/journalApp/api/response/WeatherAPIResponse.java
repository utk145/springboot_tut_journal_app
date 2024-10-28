package net.engineeringdigest.journalApp.api.response;


//https://json2csharp.com/code-converters/json-to-pojo


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherAPIResponse {
    private Current current;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Condition {
        private String text;
        private String icon;
        private int code;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Current {
        private int last_updated_epoch;
        private String last_updated;
        private double temp_c;
        private double temp_f;
        private int is_day;
        private Condition condition;
        private double wind_mph;
        private double wind_kph;
        private int wind_degree;
        private String wind_dir;
        private double pressure_mb;
        private double pressure_in;
        private double precip_mm;
        private double precip_in;
        private int humidity;
        private int cloud;
        private double feelslike_c;
        private double feelslike_f;
        private double windchill_c;
        private double windchill_f;
        private double heatindex_c;
        private double heatindex_f;
        private double dewpoint_c;
        private double dewpoint_f;
        private double vis_km;
        private double vis_miles;
        private double uv;
        private double gust_mph;
        private double gust_kph;
    }
}
