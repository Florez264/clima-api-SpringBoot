package clima.climaapi.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ciudades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "codigo_pais")
    private String codigoPais;

    private Double Latitud;
    private Double Longitud;

    @OneToMany(mappedBy = "ciudad", cascade = CascadeType.ALL)
    private List<Consulta> consultas;


}
