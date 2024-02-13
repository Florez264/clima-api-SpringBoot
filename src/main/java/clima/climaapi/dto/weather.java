package clima.climaapi.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class weather {
    private int id;
    private String main;
    private String description;
    private String icon;
}
