package com.example.mascotas.mascotasServicios;

import com.example.mascotas.mascotas.Mascota;
import com.example.mascotas.mascotas.MascotaRepository;
import com.example.mascotas.servicios.Servicio;
import com.example.mascotas.servicios.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/mascota-servicio")
public class MascotaServicioController {

    @Autowired
    private MascotaServicioRepository mascotaServicioRepository;
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping
    public ResponseEntity<Iterable<MascotaServicio>> findAll() {
        return ResponseEntity.ok(mascotaServicioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaServicio> findById(@PathVariable Long id) {
        Optional<MascotaServicio> mascotaServicio = mascotaServicioRepository.findById(id);
        if (mascotaServicio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mascotaServicio.get());
    }

    @PostMapping
    public ResponseEntity<MascotaServicio> create(@RequestBody MascotaServicio mascotaServicio, UriComponentsBuilder uriBuilder) {
        Optional<Mascota> mascotaOpcional = mascotaRepository.findById(mascotaServicio.getMascota().getIdMascota());
        if (mascotaOpcional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Optional<Servicio> servicioOpcional = servicioRepository.findById(mascotaServicio.getServicio().getIdServicio());
        if (servicioOpcional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        mascotaServicio.setMascota(mascotaOpcional.get());
        mascotaServicio.setServicio(servicioOpcional.get());
        MascotaServicio guardado = mascotaServicioRepository.save(mascotaServicio);
        URI url = uriBuilder.path("/mascota-servicio/{id}")
                .buildAndExpand(guardado.getIdMascotaServicio()).toUri();
        return ResponseEntity.created(url).body(guardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<MascotaServicio> mascotaServicio = mascotaServicioRepository.findById(id);
        if (mascotaServicio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mascotaServicioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}