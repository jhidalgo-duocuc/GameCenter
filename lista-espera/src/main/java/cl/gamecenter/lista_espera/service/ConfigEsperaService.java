package cl.gamecenter.lista_espera.service;

import cl.gamecenter.lista_espera.dto.ConfigEsperaRequestDTO;
import cl.gamecenter.lista_espera.dto.ConfigEsperaResponseDTO;
import cl.gamecenter.lista_espera.entity.ConfigEsperaEntity;
import cl.gamecenter.lista_espera.repository.ConfigEsperaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ConfigEsperaService {

    private final ConfigEsperaRepository configEsperaRepository;

    public ConfigEsperaService(ConfigEsperaRepository configEsperaRepository) {
        this.configEsperaRepository = configEsperaRepository;
    }

    public ConfigEsperaResponseDTO crear(ConfigEsperaRequestDTO request) {
        ConfigEsperaEntity guardado = configEsperaRepository.save(toEntity(request));
        return toResponse(guardado);
    }

    public ConfigEsperaResponseDTO obtenerPorId(Long id) {
        ConfigEsperaEntity entity = configEsperaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Configuracion de espera no encontrada"));
        return toResponse(entity);
    }

    public List<ConfigEsperaResponseDTO> listar() {
        return configEsperaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ConfigEsperaResponseDTO actualizar(Long id, ConfigEsperaRequestDTO request) {
        ConfigEsperaEntity entity = configEsperaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Configuracion de espera no encontrada"));
        aplicarRequest(entity, request);
        return toResponse(configEsperaRepository.save(entity));
    }

    public void eliminar(Long id) {
        if (!configEsperaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Configuracion de espera no encontrada");
        }
        configEsperaRepository.deleteById(id);
    }

    private ConfigEsperaEntity toEntity(ConfigEsperaRequestDTO request) {
        ConfigEsperaEntity entity = new ConfigEsperaEntity();
        aplicarRequest(entity, request);
        return entity;
    }

    private void aplicarRequest(ConfigEsperaEntity entity, ConfigEsperaRequestDTO request) {
        entity.setMinutosParaConfirmar(request.getMinutosParaConfirmar());
        entity.setMaxIntentos(request.getMaxIntentos());
        entity.setActivo(request.getActivo());
    }

    private ConfigEsperaResponseDTO toResponse(ConfigEsperaEntity entity) {
        ConfigEsperaResponseDTO response = new ConfigEsperaResponseDTO();
        response.setId(entity.getId());
        response.setMinutosParaConfirmar(entity.getMinutosParaConfirmar());
        response.setMaxIntentos(entity.getMaxIntentos());
        response.setActivo(entity.getActivo());
        return response;
    }
}
