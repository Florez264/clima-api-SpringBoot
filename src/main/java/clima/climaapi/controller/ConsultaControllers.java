package clima.climaapi.controller;


import clima.climaapi.dto.ClimaData;
import clima.climaapi.dto.pronostico.contami.Contaminacion;
import clima.climaapi.dto.pronostico.map.PronosticoData;
import clima.climaapi.model.Ciudad;
import clima.climaapi.segurity.service.UsuarioService;
import clima.climaapi.service.CiudadService;
import clima.climaapi.service.ConsultaService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.google.common.util.concurrent.RateLimiter;


@RestController
@RequestMapping("/consulta/")
@RequiredArgsConstructor
public class ConsultaControllers {

    private final CiudadService ciudadService;
    private final ConsultaService consultaService;
    private final UsuarioService usuarioService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RateLimiter rateLimiter;

    public String welcome()
    {
        return "Ya puedes realizar las consultas del Clima";
    }


    @PostMapping("/consulta")
    public ResponseEntity<ClimaData> consultarClima(@RequestParam("ciudad") String ciudad) {
        if (!rateLimiter.tryAcquire()) {
            // Si no se puede adquirir el permiso, devolver un error 429 Too Many Requests
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Long usuarioId = usuarioService.obtenerIdPorNombreUsuario(username);

        Long ciudadId = ciudadService.obtenerIdPorNombreCiudad(ciudad);
        if (ciudadId == null) {
            Ciudad nuevaCiudad = new Ciudad();
            nuevaCiudad.setNombre(ciudad);
            ciudadId = ciudadService.registrarCiudad(nuevaCiudad);
        }
        ClimaData weatherData = ciudadService.getWeatherData(ciudad);

        consultaService.guardarConsultaClimaActual(ciudadId, usuarioId);

        return ResponseEntity.ok().body(weatherData);

    }

    @PostMapping("/pronostico")
    public ResponseEntity<PronosticoData> consultarPronostico(@RequestParam("ciudad") String ciudad){
       
        if (!rateLimiter.tryAcquire()) {
            // Si no se puede adquirir el permiso, devolver un error 429 Too Many Requests
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Long usuarioId = usuarioService.obtenerIdPorNombreUsuario(username);
        Long ciudadId = ciudadService.obtenerIdPorNombreCiudad(ciudad);

        if (ciudadId == null) {
            Ciudad nuevaCiudad = new Ciudad();
            nuevaCiudad.setNombre(ciudad);
            ciudadId = ciudadService.registrarCiudad(nuevaCiudad);
        }
        PronosticoData weatherData = ciudadService.getPronostiData(ciudad);

        consultaService.guardarConsultaPronostico(ciudadId, usuarioId);

        return ResponseEntity.ok().body(weatherData);
    }


    @PostMapping("/contaminacion")
    public ResponseEntity<Contaminacion> consultarContaminacion(@RequestParam("ciudad") String ciudad) {

        if (!rateLimiter.tryAcquire()) {
            // Si no se puede adquirir el permiso, devolver un error 429 Too Many Requests
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(null);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Long usuarioId = usuarioService.obtenerIdPorNombreUsuario(username);

        Ciudad ciudadEncontrada = ciudadService.findByNombre(ciudad);
        if (ciudadEncontrada == null) {
            Ciudad nuevaCiudad = new Ciudad();
            nuevaCiudad.setNombre(ciudad);
            Long ciudadId = ciudadService.registrarCiudad(nuevaCiudad);
            ciudadEncontrada = ciudadService.findById(ciudadId); 
        }

        double latitud = ciudadEncontrada.getLatitud();
        double longitud = ciudadEncontrada.getLongitud();

        String apiUrl = "http://api.openweathermap.org/data/2.5/air_pollution?lat=" + latitud + "&lon=" + longitud + "&appid=e783436250a79097272367d5ddb1c2d7";

        Contaminacion contaminacionData = restTemplate.getForObject(apiUrl, Contaminacion.class);

        consultaService.guardarConsultaContaminacion(ciudadEncontrada.getId(), usuarioId);

        return ResponseEntity.ok().body(contaminacionData);
    }


}
