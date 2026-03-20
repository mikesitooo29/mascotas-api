package com.example.mascotas.mascotasServicios;

import com.example.mascotas.mascotas.Mascota;
import com.example.mascotas.servicios.Servicio;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name= "mascota-servicio")
public class MascotaServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMascotaServicio;
    private Date fecha;
    @Column(length = 100)
    private String nota;

    @ManyToOne
    @JoinColumn(name = "idMascota")
    @JsonIgnoreProperties({"mascotaServicios", "cliente"})
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "idServicio")
    @JsonIgnoreProperties({"mascotaServicios"})
    private Servicio servicio;
}