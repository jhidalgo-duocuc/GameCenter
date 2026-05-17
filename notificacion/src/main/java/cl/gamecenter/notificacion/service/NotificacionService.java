package cl.gamecenter.notificacion.service;

import cl.gamecenter.notificacion.client.UsuarioClient;
import cl.gamecenter.notificacion.dto.NotificacionRequestDTO;
import cl.gamecenter.notificacion.dto.NotificacionResponseDTO;
import cl.gamecenter.notificacion.dto.UsuarioClientDTO;
import cl.gamecenter.notificacion.entity.NotificacionEntity;
import cl.gamecenter.notificacion.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioClient usuarioClient;

    public NotificacionResponseDTO crear(NotificacionRequestDTO request) {
        UsuarioClientDTO usuario = usuarioClient.buscarPorId(request.getUsuarioId());
        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new RuntimeException("El usuario no está activo");
        }

        NotificacionEntity guardado = notificacionRepository.save(toEntity(request));
        return toResponse(guardado);
    }

    public NotificacionResponseDTO obtenerPorId(Long id) {
        NotificacionEntity entity = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificacion no encontrada"));
        return toResponse(entity);
    }

    public List<NotificacionResponseDTO> listar() {
        return notificacionRepository.findAll().stream().map(this::toResponse).toList();
    }

    public NotificacionResponseDTO actualizar(Long id, NotificacionRequestDTO request) {
        NotificacionEntity entity = notificacionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificacion no encontrada"));
        aplicarRequest(entity, request);
        return toResponse(notificacionRepository.save(entity));
    }

    public void eliminar(Long id) {
        if (!notificacionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificacion no encontrada");
        }
        notificacionRepository.deleteById(id);
    }

    private NotificacionEntity toEntity(NotificacionRequestDTO request) {
        NotificacionEntity entity = new NotificacionEntity();
        aplicarRequest(entity, request);
        return entity;
    }

    private void aplicarRequest(NotificacionEntity entity, NotificacionRequestDTO request) {
        entity.setUsuarioId(request.getUsuarioId());
        entity.setTipo(request.getTipo());
        entity.setTitulo(request.getTitulo());
        entity.setMensaje(request.getMensaje());
        entity.setLeida(request.getLeida());
        entity.setCanal(request.getCanal());
        entity.setCreatedAt(request.getCreatedAt());
    }

    private NotificacionResponseDTO toResponse(NotificacionEntity entity) {
        NotificacionResponseDTO response = new NotificacionResponseDTO();
        response.setId(entity.getId());
        response.setUsuarioId(entity.getUsuarioId());
        response.setTipo(entity.getTipo());
        response.setTitulo(entity.getTitulo());
        response.setMensaje(entity.getMensaje());
        response.setLeida(entity.getLeida());
        response.setCanal(entity.getCanal());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }
}
