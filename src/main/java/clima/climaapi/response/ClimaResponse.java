package clima.climaapi.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ClimaResponse {

    private String city;
    private double temperature;
    private String weatherDescription;

    public ClimaResponse(String city, double temperature, String weatherDescription) {
        this.city = city;
        this.temperature = temperature;
        this.weatherDescription = weatherDescription;
    }
}
