package clima.climaapi.service;


import clima.climaapi.model.Ciudad;
import clima.climaapi.model.Consulta;
import clima.climaapi.model.Usuario;
import clima.climaapi.repository.CiudadRepository;
import clima.climaapi.repository.ConsultaRepository;
import clima.climaapi.segurity.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CiudadRepository ciudadRepository;

    public void guardarConsultaClimaActual(Long ciudadId, Long usuarioId) {

        LocalDateTime localDateTime = LocalDateTime.now();
        Date fechaConsulta = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        Ciudad ciudad = ciudadRepository.findById(ciudadId).orElse(null);

        Consulta consulta = new Consulta();
        consulta.setFecha_consulta(fechaConsulta);
        consulta.setTipoConsulta("clima actual");
        consulta.setCiudad(ciudad);
        consulta.setUsuario(usuario);
        consultaRepository.save(consulta);
    }

    public void guardarConsultaPronostico(Long ciudadId, Long usuarioId) {

        LocalDateTime localDateTime = LocalDateTime.now();
        Date fechaConsulta = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        Ciudad ciudad = ciudadRepository.findById(ciudadId).orElse(null);

        Consulta consulta = new Consulta();
        consulta.setFecha_consulta(fechaConsulta);
        consulta.setTipoConsulta("Pronostico 5 dias");
        consulta.setCiudad(ciudad);
        consulta.setUsuario(usuario);
        consultaRepository.save(consulta);
    }

    public void guardarConsultaContaminacion(Long ciudadId, Long usuarioId) {

        LocalDateTime localDateTime = LocalDateTime.now();
        Date fechaConsulta = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        Ciudad ciudad = ciudadRepository.findById(ciudadId).orElse(null);

        Consulta consulta = new Consulta();
        consulta.setFecha_consulta(fechaConsulta);
        consulta.setTipoConsulta("Contaminacion en el Aire");
        consulta.setCiudad(ciudad);
        consulta.setUsuario(usuario);
        consultaRepository.save(consulta);
    }

}
