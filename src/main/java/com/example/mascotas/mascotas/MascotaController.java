package com.example.mascotas.mascotas;

import com.example.mascotas.clientes.Cliente;
import com.example.mascotas.clientes.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/mascota")
public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<Iterable<Mascota>> findAll() {
        return ResponseEntity.ok(mascotaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> findById(@PathVariable Long id) {
        Optional<Mascota> mascota = mascotaRepository.findById(id);
        if (mascota.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mascota.get());
    }

    @PostMapping
    public ResponseEntity<Mascota> create(@RequestBody Mascota mascota, UriComponentsBuilder uriBuilder) {
        Optional<Cliente> clienteOpcional = clienteRepository.findById(mascota.getCliente().getIdCliente());
        if (clienteOpcional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        mascota.setCliente(clienteOpcional.get());
        Mascota mascotaGuardada = mascotaRepository.save(mascota);
        URI url = uriBuilder.path("/mascota/{id}")
                .buildAndExpand(mascotaGuardada.getIdMascota()).toUri();
        return ResponseEntity.created(url).body(mascotaGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> update(@PathVariable Long id, @RequestBody Mascota mascota) {
        Optional<Mascota> mascotaOpcional = mascotaRepository.findById(id);
        if (mascotaOpcional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Cliente> clienteOpcional = clienteRepository.findById(mascota.getCliente().getIdCliente());
        if (clienteOpcional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        mascota.setIdMascota(id);
        mascota.setCliente(clienteOpcional.get());
        return ResponseEntity.ok(mascotaRepository.save(mascota));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Mascota> mascotaOpcional = mascotaRepository.findById(id);
        if (mascotaOpcional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        mascotaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}