package clima.climaapi.controller;

import clima.climaapi.config.SecurityConfig;
import clima.climaapi.dto.ClimaData;
import clima.climaapi.segurity.jwt.JwtAuthenticationFilter;
import clima.climaapi.segurity.jwt.JwtService;
import clima.climaapi.segurity.service.UsuarioService;
import clima.climaapi.service.CiudadService;
import clima.climaapi.service.ConsultaService;
import com.google.common.util.concurrent.RateLimiter;
import com.mysql.cj.protocol.AuthenticationProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsultaControllers.class)
@ContextConfiguration(classes = { JwtAuthenticationFilter.class, SecurityConfig.class })
public class ConsultaControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsultaService consultaService;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private CiudadService ciudadService;

    @MockBean
    private RateLimiter rateLimiter;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationProvider authenticationProvider;


    @Test
    public void consultarClima_authenticatedUser_shouldReturnWeatherData() throws Exception {

        UserDetails userDetails = new User("username", "password", Collections.emptyList());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        
        ClimaData weatherData = new ClimaData();
        when(ciudadService.getWeatherData(anyString())).thenReturn(weatherData);

        mockMvc.perform(MockMvcRequestBuilders.post("/consulta/consulta")
                .param("ciudad", "BOGOTA"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("BOGOTA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.weatherDescription").exists());

        verify(consultaService).guardarConsultaClimaActual(anyLong(), anyLong());
    }


}
