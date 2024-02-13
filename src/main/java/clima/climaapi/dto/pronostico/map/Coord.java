package clima.climaapi.dto.pronostico.map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Coord {
    private double lat;
    private double lon;
}
