package com.example.mascotas.mascotas;

import com.example.mascotas.clientes.Cliente;
import com.example.mascotas.mascotasServicios.MascotaServicio;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name= "mascotas")
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMascota;
    @Column(nullable = false, length = 100)
    private String nombre;
    private char sexo;
    @Column(nullable = false, length = 100)
    private String tipo;
    private byte edad;
    private boolean enPeligro;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idCliente")
    @JsonIgnoreProperties({"direccion", "mascotas"})
    private Cliente cliente;

    @JsonIgnoreProperties({"mascota"})
    @OneToMany(mappedBy = "mascota")
    private List<MascotaServicio> mascotaServicios;
}