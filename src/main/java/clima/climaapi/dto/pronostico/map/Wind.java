package clima.climaapi.dto.pronostico.map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wind {
    private String deg;
    private double gust;
    private double speed;
}
