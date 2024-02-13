package clima.climaapi.service;



import clima.climaapi.dto.ClimaData;
import clima.climaapi.dto.pronostico.map.PronosticoData;
import clima.climaapi.model.Ciudad;
import clima.climaapi.repository.CiudadRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Service
public class CiudadService {

     private static final Logger logger = LoggerFactory.getLogger(CiudadService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CiudadRepository ciudadRepository;

    public CiudadService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Cacheable(value = "weatherData", key = "#cityName", unless = "#result == null")
    public ClimaData getWeatherData(String cityName) {

        logger.info("Realizando solicitud a la API externa para obtener datos climáticos de la ciudad: {}", cityName);
        
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid=e783436250a79097272367d5ddb1c2d7&units=metric&lang=es";

        ResponseEntity<ClimaData> response = restTemplate.getForEntity(url, ClimaData.class);
        ClimaData weatherData = response.getBody();
        saveWeatherData(weatherData);

        //logger.info("Datos climáticos de la ciudad {} obtenidos correctamente", cityName);

        return weatherData;
    }

    public Long registrarCiudad(Ciudad nuevaCiudad) {
        Ciudad ciudadRegistrada = ciudadRepository.save(nuevaCiudad);
        return ciudadRegistrada.getId();
    }
    private void saveWeatherData(ClimaData weatherData) {
        String cityName = weatherData.getName();

        Optional<Ciudad> existingCityOptional = ciudadRepository.findByNombre(cityName);

        if (existingCityOptional.isPresent()) {
            Ciudad existingCity = existingCityOptional.get();
            actualizarDatosClima(existingCity, weatherData);
        } else {
            Ciudad newCity = new Ciudad();
            newCity.setNombre(cityName);
            newCity.setLatitud(weatherData.getCoord().getLat());
            newCity.setLongitud(weatherData.getCoord().getLon());
            ciudadRepository.save(newCity);
        }
    }

    private void actualizarDatosClima(Ciudad ciudad, ClimaData weatherData) {

        ciudad.setLongitud(weatherData.getCoord().getLon());
        ciudad.setLatitud(weatherData.getCoord().getLat());
        ciudadRepository.save(ciudad);
    }

    public Long obtenerIdPorNombreCiudad(String nombreCiudad) {
        Optional<Ciudad> ciudad = ciudadRepository.findByNombre(nombreCiudad);
        if (ciudad.isPresent()) {
            return ciudad.get().getId();
        } else {
            return null;
        }
    }

    public Ciudad findByNombre(String nombreCiudad) {
        return ciudadRepository.findByNombre(nombreCiudad).orElse(null);
    }

    //Guardar Pronostico

    public PronosticoData getPronostiData(String cityName) {
        String url ="https://api.openweathermap.org/data/2.5/forecast?q="+cityName+"&appid=e783436250a79097272367d5ddb1c2d7&units=metric&lang=es"; 

        ResponseEntity<PronosticoData> response = restTemplate.getForEntity(url, PronosticoData.class);
        PronosticoData weatherData = response.getBody();
        GuardarPronostData(weatherData);
        return weatherData;
    }

    private void GuardarPronostData(PronosticoData pronosticoData) {
        String cityName = pronosticoData.getCity().getName();

        Optional<Ciudad> existingCityOptional = ciudadRepository.findByNombre(cityName);

        if (existingCityOptional.isPresent()) {
            Ciudad existingCity = existingCityOptional.get();
            actualizarDatos(existingCity, pronosticoData);
        } else {
            Ciudad newCity = new Ciudad();
            newCity.setNombre(cityName);
            newCity.setLatitud(pronosticoData.getCity().getCoord().getLat());
            newCity.setLongitud(pronosticoData.getCity().getCoord().getLon());
            ciudadRepository.save(newCity);
        }
    }

    private void actualizarDatos(Ciudad ciudad, PronosticoData pronosticoData) {

        ciudad.setLongitud(pronosticoData.getCity().getCoord().getLon());
        ciudad.setLatitud(pronosticoData.getCity().getCoord().getLat());
        ciudadRepository.save(ciudad);
    }

    public Ciudad findById(Long ciudadId) {
        return ciudadRepository.findById(ciudadId).orElse(null);
    }

    // Contaminacion


}
