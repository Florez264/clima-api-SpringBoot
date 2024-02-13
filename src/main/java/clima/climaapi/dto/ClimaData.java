package clima.climaapi.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ClimaData {
    private Coord coord;
    private List<weather> weather;
    private Main main;
    private Wind wind;
    private String name;
    private Sys sys;
}
