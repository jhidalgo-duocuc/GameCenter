package cl.gamecenter.control_tiempo.service;

import cl.gamecenter.control_tiempo.client.EstacionClient;
import cl.gamecenter.control_tiempo.client.dto.EstacionClientDTO;
import cl.gamecenter.control_tiempo.dto.SesionRequestDTO;
import cl.gamecenter.control_tiempo.dto.SesionResponseDTO;
import cl.gamecenter.control_tiempo.entity.SesionEntity;
import cl.gamecenter.control_tiempo.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SesionServiceTest {

    @Mock
    private SessionRepository sesionRepository;

    @Mock
    private EstacionClient estacionClient;

    @InjectMocks
    private SesionService sesionService;

    private SesionRequestDTO requestDTO;
    private SesionEntity entidadActiva;
    private EstacionClientDTO estacionDTO;

    @BeforeEach
    void setUp() {
        // DTO de request base
        requestDTO = new SesionRequestDTO();
        requestDTO.setReservaId(1L);
        requestDTO.setEstacionId(10L);
        requestDTO.setUsuarioId(5L);
        requestDTO.setTarifaPorHora(new BigDecimal("3000.00"));

        // Entidad activa simulada
        entidadActiva = new SesionEntity();
        entidadActiva.setId(1L);
        entidadActiva.setReservaId(1L);
        entidadActiva.setEstacionId(10L);
        entidadActiva.setUsuarioId(5L);
        entidadActiva.setTarifaPorHora(new BigDecimal("3000.00"));
        entidadActiva.setEstado(SesionEntity.EstadoSesion.ACTIVA);
        entidadActiva.setInicioReal(LocalDateTime.now().minusMinutes(30));

        // DTO estacion simulada
        estacionDTO = new EstacionClientDTO();
        estacionDTO.setId(10L);
        estacionDTO.setEstado("OCUPADA");
    }


    // TESTS: iniciar()

    @Test
    @DisplayName("iniciar() - debe crear sesión cuando la estación no tiene sesión activa")
    void iniciar_debeCrearSesion_cuandoEstacionDisponible() {
        when(sesionRepository.findByEstacionIdAndEstado(10L, SesionEntity.EstadoSesion.ACTIVA))
                .thenReturn(Optional.empty());
        when(estacionClient.cambiarEstado(10L, "OCUPADA")).thenReturn(estacionDTO);
        when(sesionRepository.save(any(SesionEntity.class))).thenReturn(entidadActiva);

        SesionResponseDTO resultado = sesionService.iniciar(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getEstacionId()).isEqualTo(10L);
        assertThat(resultado.getUsuarioId()).isEqualTo(5L);
        assertThat(resultado.getEstado()).isEqualTo("ACTIVA");
        verify(estacionClient).cambiarEstado(10L, "OCUPADA");
        verify(sesionRepository).save(any(SesionEntity.class));
    }

    @Test
    @DisplayName("iniciar() - debe lanzar excepción cuando ya existe sesión activa en la estación")
    void iniciar_debeLanzarExcepcion_cuandoYaHaySesionActiva() {
        when(sesionRepository.findByEstacionIdAndEstado(10L, SesionEntity.EstadoSesion.ACTIVA))
                .thenReturn(Optional.of(entidadActiva));

        assertThatThrownBy(() -> sesionService.iniciar(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("ya tiene una sesion activa");

        verify(estacionClient, never()).cambiarEstado(anyLong(), anyString());
        verify(sesionRepository, never()).save(any());
    }

    // TESTS: cerrar()

    @Test
    @DisplayName("cerrar() - debe cerrar sesión activa y calcular costo correctamente")
    void cerrar_debeCerrarSesion_cuandoEstaActiva() {
        // GIVEN
        entidadActiva.setInicioReal(LocalDateTime.now().minusMinutes(60));
        when(sesionRepository.findById(1L)).thenReturn(Optional.of(entidadActiva));

        SesionEntity entidadCerrada = new SesionEntity();
        entidadCerrada.setId(1L);
        entidadCerrada.setEstacionId(10L);
        entidadCerrada.setUsuarioId(5L);
        entidadCerrada.setTarifaPorHora(new BigDecimal("3000.00"));
        entidadCerrada.setEstado(SesionEntity.EstadoSesion.CERRADA);
        entidadCerrada.setInicioReal(entidadActiva.getInicioReal());
        entidadCerrada.setFinReal(LocalDateTime.now());
        entidadCerrada.setMinutosConsumidos(60);
        entidadCerrada.setTotalCalculado(new BigDecimal("3000.00"));

        when(estacionClient.cambiarEstado(10L, "DISPONIBLE")).thenReturn(estacionDTO);
        when(sesionRepository.save(any(SesionEntity.class))).thenReturn(entidadCerrada);

        // WHEN
        SesionResponseDTO resultado = sesionService.cerrar(1L);

        // THEN
        assertThat(resultado).isNotNull();
        assertThat(resultado.getEstado()).isEqualTo("CERRADA");
        assertThat(resultado.getMinutosConsumidos()).isGreaterThan(0);
        assertThat(resultado.getTotalCalculado()).isGreaterThan(BigDecimal.ZERO);
        verify(estacionClient).cambiarEstado(10L, "DISPONIBLE");
        verify(sesionRepository).save(any(SesionEntity.class));
    }

    @Test
    @DisplayName("cerrar() - debe lanzar excepción cuando la sesión no existe")
    void cerrar_debeLanzarExcepcion_cuandoSesionNoExiste() {
        // GIVEN
        when(sesionRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThatThrownBy(() -> sesionService.cerrar(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Sesion no encontrada");

        verify(estacionClient, never()).cambiarEstado(anyLong(), anyString());
    }

    @Test
    @DisplayName("cerrar() - debe lanzar excepción cuando la sesión ya está cerrada")
    void cerrar_debeLanzarExcepcion_cuandoSesionYaCerrada() {
        // GIVEN
        entidadActiva.setEstado(SesionEntity.EstadoSesion.CERRADA);
        when(sesionRepository.findById(1L)).thenReturn(Optional.of(entidadActiva));

        // WHEN & THEN
        assertThatThrownBy(() -> sesionService.cerrar(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no está activa");

        verify(estacionClient, never()).cambiarEstado(anyLong(), anyString());
    }

    @Test
    @DisplayName("cerrar() - sesión con menos de 1 minuto debe cobrar mínimo 1 minuto")
    void cerrar_debeCalcularMinimoUnMinuto_cuandoSesionMuyCorta() {
        // GIVEN - sesión iniciada hace apenas segundos
        entidadActiva.setInicioReal(LocalDateTime.now().minusSeconds(10));
        when(sesionRepository.findById(1L)).thenReturn(Optional.of(entidadActiva));

        SesionEntity entidadCerrada = new SesionEntity();
        entidadCerrada.setId(1L);
        entidadCerrada.setEstacionId(10L);
        entidadCerrada.setUsuarioId(5L);
        entidadCerrada.setTarifaPorHora(new BigDecimal("3000.00"));
        entidadCerrada.setEstado(SesionEntity.EstadoSesion.CERRADA);
        entidadCerrada.setInicioReal(entidadActiva.getInicioReal());
        entidadCerrada.setFinReal(LocalDateTime.now());
        entidadCerrada.setMinutosConsumidos(1);
        entidadCerrada.setTotalCalculado(new BigDecimal("50.00"));

        when(estacionClient.cambiarEstado(10L, "DISPONIBLE")).thenReturn(estacionDTO);
        when(sesionRepository.save(any(SesionEntity.class))).thenReturn(entidadCerrada);

        // WHEN
        SesionResponseDTO resultado = sesionService.cerrar(1L);

        // THEN
        assertThat(resultado.getMinutosConsumidos()).isGreaterThanOrEqualTo(1);
    }

    // TESTS: buscarPorId()
    @Test
    @DisplayName("buscarPorId() - debe retornar sesión cuando existe")
    void buscarPorId_debeRetornarSesion_cuandoExiste() {
        when(sesionRepository.findById(1L)).thenReturn(Optional.of(entidadActiva));

        SesionResponseDTO resultado = sesionService.buscarPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getEstado()).isEqualTo("ACTIVA");
    }

    @Test
    @DisplayName("buscarPorId() - debe lanzar excepción cuando no existe")
    void buscarPorId_debeLanzarExcepcion_cuandoNoExiste() {
        when(sesionRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> sesionService.buscarPorId(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Sesion no encontrada");
    }

    // TESTS: listar() y listarActivas()
    @Test
    @DisplayName("listar() - debe retornar todas las sesiones")
    void listar_debeRetornarTodasLasSesiones() {
        when(sesionRepository.findAll()).thenReturn(List.of(entidadActiva));

        List<SesionResponseDTO> resultado = sesionService.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("listarActivas() - debe retornar solo sesiones activas")
    void listarActivas_debeRetornarSoloSesionesActivas() {
        when(sesionRepository.findByEstado(SesionEntity.EstadoSesion.ACTIVA))
                .thenReturn(List.of(entidadActiva));

        List<SesionResponseDTO> resultado = sesionService.listarActivas();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getEstado()).isEqualTo("ACTIVA");
    }

    @Test
    @DisplayName("listarPorUsuario() - debe retornar sesiones del usuario indicado")
    void listarPorUsuario_debeRetornarSesionesDelUsuario() {
        when(sesionRepository.findByUsuarioId(5L)).thenReturn(List.of(entidadActiva));

        List<SesionResponseDTO> resultado = sesionService.listarPorUsuario(5L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getUsuarioId()).isEqualTo(5L);
    }
}