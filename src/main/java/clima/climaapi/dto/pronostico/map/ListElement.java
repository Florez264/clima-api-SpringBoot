package clima.climaapi.dto.pronostico.map;



import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListElement {
 private Clouds clouds;
 private long dt;
 private String dtTxt;
 private String main;
 private double pop;
 private Rain rain;
 private Sys sys;
 private long visibility;
 private ArrayList<Weather> weather;
 private Wind wind;
}
