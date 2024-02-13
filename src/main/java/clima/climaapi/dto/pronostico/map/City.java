package clima.climaapi.dto.pronostico.map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class City {
    
    private Coord coord;
    private String country;
    private long id;
    private String name;
    private long population;
    private long sunrise;
    private long sunset;
    private long timezone;
}
