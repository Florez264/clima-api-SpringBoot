package clima.climaapi.dto.pronostico.map;

import org.springframework.context.annotation.Description;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weather {
    
private Description description;
 private String icon;
 private String id;
 private String main;
}
