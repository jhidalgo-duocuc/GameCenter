package cl.gamecenter.lista_espera.service;

import cl.gamecenter.lista_espera.dto.EntradaEsperaRequestDTO;
import cl.gamecenter.lista_espera.dto.EntradaEsperaResponseDTO;
import cl.gamecenter.lista_espera.entity.EntradaEsperaEntity;
import cl.gamecenter.lista_espera.repository.EntradaEsperaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EntradaEsperaService {

    private final EntradaEsperaRepository entradaEsperaRepository;

    public EntradaEsperaService(EntradaEsperaRepository entradaEsperaRepository) {
        this.entradaEsperaRepository = entradaEsperaRepository;
    }

    public EntradaEsperaResponseDTO crear(EntradaEsperaRequestDTO request) {
        EntradaEsperaEntity guardado = entradaEsperaRepository.save(toEntity(request));
        return toResponse(guardado);
    }

    public EntradaEsperaResponseDTO obtenerPorId(Long id) {
        EntradaEsperaEntity entity = entradaEsperaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrada de espera no encontrada"));
        return toResponse(entity);
    }

    public List<EntradaEsperaResponseDTO> listar() {
        return entradaEsperaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public EntradaEsperaResponseDTO actualizar(Long id, EntradaEsperaRequestDTO request) {
        EntradaEsperaEntity entity = entradaEsperaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrada de espera no encontrada"));
        aplicarRequest(entity, request);
        return toResponse(entradaEsperaRepository.save(entity));
    }

    public void eliminar(Long id) {
        if (!entradaEsperaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrada de espera no encontrada");
        }
        entradaEsperaRepository.deleteById(id);
    }

    private EntradaEsperaEntity toEntity(EntradaEsperaRequestDTO request) {
        EntradaEsperaEntity entity = new EntradaEsperaEntity();
        aplicarRequest(entity, request);
        return entity;
    }

    private void aplicarRequest(EntradaEsperaEntity entity, EntradaEsperaRequestDTO request) {
        entity.setUsuarioId(request.getUsuarioId());
        entity.setTipoEstacionId(request.getTipoEstacionId());
        entity.setPosicion(request.getPosicion());
        entity.setEstado(request.getEstado());
        entity.setFechaIngreso(request.getFechaIngreso());
        entity.setFechaNotificacion(request.getFechaNotificacion());
        entity.setExpiraEn(request.getExpiraEn());
    }

    private EntradaEsperaResponseDTO toResponse(EntradaEsperaEntity entity) {
        EntradaEsperaResponseDTO response = new EntradaEsperaResponseDTO();
        response.setId(entity.getId());
        response.setUsuarioId(entity.getUsuarioId());
        response.setTipoEstacionId(entity.getTipoEstacionId());
        response.setPosicion(entity.getPosicion());
        response.setEstado(entity.getEstado());
        response.setFechaIngreso(entity.getFechaIngreso());
        response.setFechaNotificacion(entity.getFechaNotificacion());
        response.setExpiraEn(entity.getExpiraEn());
        return response;
    }
}
