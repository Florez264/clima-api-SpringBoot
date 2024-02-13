package clima.climaapi.dto.pronostico.contami;

import java.util.ArrayList;

import clima.climaapi.dto.Coord;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Contaminacion {
     private Coord coord;
     private  ArrayList<Lista> list;
}
