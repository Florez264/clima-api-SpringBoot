package clima.climaapi.dto.pronostico.map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Main {
    private double temp;
    private double feelsLike;
    private double tempMin;
    private double tempMax;
    private int pressure;
    private int seaLevel;
    private int grndLevel;
    private int humidity;
    private double tempKf;
}
