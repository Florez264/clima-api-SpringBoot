package clima.climaapi.dto.pronostico.map;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PronosticoData {
    private City city;
    private long cnt;
    private String cod;
   // private ArrayList<ListElement> list;
    private long message;
    
}
