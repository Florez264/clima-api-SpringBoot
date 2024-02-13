package clima.climaapi.dto.pronostico.contami;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Lista {
    private Map<String, Double> components;
    private long dt;
    private Main main;
}
