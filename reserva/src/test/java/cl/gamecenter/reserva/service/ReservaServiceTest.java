package cl.gamecenter.reserva.service;
import cl.gamecenter.reserva.client.EstacionClient;
import cl.gamecenter.reserva.dto.EstacionClientDTO;
import cl.gamecenter.reserva.dto.ReservaRequestDTO;
import cl.gamecenter.reserva.dto.ReservaResponseDTO;
import cl.gamecenter.reserva.entity.ReservaEntity;
import cl.gamecenter.reserva.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private EstacionClient estacionClient;

    @InjectMocks
    private ReservaService reservaService;

    private ReservaRequestDTO requestDTO;
    private EstacionClientDTO estacionDisponible;
    private ReservaEntity entidadGuardada;

    @BeforeEach
    void setUp() {
        // DTO de request base
        requestDTO = new ReservaRequestDTO();
        requestDTO.setUsuarioId(1L);
        requestDTO.setEstacionId(10L);
        requestDTO.setFechaInicio(LocalDateTime.now().plusDays(1));
        requestDTO.setFechaFin(LocalDateTime.now().plusDays(1).plusHours(2));
        requestDTO.setNotas("Reserva de prueba");

        // Estacion disponible simulada
        estacionDisponible = new EstacionClientDTO();
        estacionDisponible.setId(10L);
        estacionDisponible.setNombre("PC Gaming 1");
        estacionDisponible.setEstado("DISPONIBLE");

        // Entidad que devuelve el repositorio al guardar
        entidadGuardada = new ReservaEntity();
        entidadGuardada.setId(1L);
        entidadGuardada.setUsuarioId(1L);
        entidadGuardada.setEstacionId(10L);
        entidadGuardada.setFechaInicio(requestDTO.getFechaInicio());
        entidadGuardada.setFechaFin(requestDTO.getFechaFin());
        entidadGuardada.setEstado(ReservaEntity.EstadoReserva.PENDIENTE);
        entidadGuardada.setNotas("Reserva de prueba");
        entidadGuardada.setCreatedAt(LocalDateTime.now());
    }
    // TESTS: crear()
    @Test
    @DisplayName("crear() - debe crear reserva cuando estacion está disponible y el horario libre")
    void crear_debeCrearReserva_cuandoEstacionDisponibleYHorarioLibre() {
    
        when(estacionClient.buscarPorId(10L)).thenReturn(estacionDisponible);
        when(reservaRepository
                .existsByEstacionIdAndEstadoInAndFechaInicioLessThanAndFechaFinGreaterThan(
                        anyLong(), anyList(), any(), any()))
                .thenReturn(false);
        when(reservaRepository.save(any(ReservaEntity.class))).thenReturn(entidadGuardada);

        ReservaResponseDTO resultado = reservaService.crear(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getEstacionId()).isEqualTo(10L);
        assertThat(resultado.getUsuarioId()).isEqualTo(1L);
        assertThat(resultado.getEstado()).isEqualTo("PENDIENTE");
        verify(reservaRepository, times(1)).save(any(ReservaEntity.class));
    }

    @Test
    @DisplayName("crear() - debe lanzar excepción cuando estacion NO está disponible")
    void crear_debeLanzarExcepcion_cuandoEstacionNoDisponible() {
        // GIVEN
        estacionDisponible.setEstado("OCUPADA");
        when(estacionClient.buscarPorId(10L)).thenReturn(estacionDisponible);

        assertThatThrownBy(() -> reservaService.crear(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no está disponible");

        verify(reservaRepository, never()).save(any());
    }

    @Test
    @DisplayName("crear() - debe lanzar excepción cuando hay conflicto de horario")
    void crear_debeLanzarExcepcion_cuandoHayConflictoDeHorario() {
        when(estacionClient.buscarPorId(10L)).thenReturn(estacionDisponible);
        when(reservaRepository
                .existsByEstacionIdAndEstadoInAndFechaInicioLessThanAndFechaFinGreaterThan(
                        anyLong(), anyList(), any(), any()))
                .thenReturn(true);

        assertThatThrownBy(() -> reservaService.crear(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("ya tiene una reserva en ese horario");

        verify(reservaRepository, never()).save(any());
    }

    // TESTS: buscarPorId()
    @Test
    @DisplayName("buscarPorId() - debe retornar reserva cuando existe")
    void buscarPorId_debeRetornarReserva_cuandoExiste() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));

        ReservaResponseDTO resultado = reservaService.buscarPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getEstado()).isEqualTo("PENDIENTE");
    }

    @Test
    @DisplayName("buscarPorId() - debe lanzar excepción cuando no existe")
    void buscarPorId_debeLanzarExcepcion_cuandoNoExiste() {

        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.buscarPorId(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Reserva no encontrada");
    }

    // TESTS: confirmar()

    @Test
    @DisplayName("confirmar() - debe cambiar estado a CONFIRMADA")
    void confirmar_debeCambiarEstadoAConfirmada() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));
        entidadGuardada.setEstado(ReservaEntity.EstadoReserva.CONFIRMADA);
        when(reservaRepository.save(any())).thenReturn(entidadGuardada);

        ReservaResponseDTO resultado = reservaService.confirmar(1L);

        assertThat(resultado.getEstado()).isEqualTo("CONFIRMADA");
        verify(reservaRepository).save(any(ReservaEntity.class));
    }

    @Test
    @DisplayName("confirmar() - debe lanzar excepción cuando reserva no existe")
    void confirmar_debeLanzarExcepcion_cuandoReservaNoExiste() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.confirmar(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Reserva no encontrada");
    }

    // TESTS: cancelar()
    @Test
    @DisplayName("cancelar() - debe cancelar reserva en estado PENDIENTE")
    void cancelar_debeCancelarReserva_cuandoEstadoPendiente() {
        entidadGuardada.setEstado(ReservaEntity.EstadoReserva.PENDIENTE);
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));

        ReservaEntity entidadCancelada = new ReservaEntity();
        entidadCancelada.setId(1L);
        entidadCancelada.setUsuarioId(1L);
        entidadCancelada.setEstacionId(10L);
        entidadCancelada.setFechaInicio(entidadGuardada.getFechaInicio());
        entidadCancelada.setFechaFin(entidadGuardada.getFechaFin());
        entidadCancelada.setEstado(ReservaEntity.EstadoReserva.CANCELADA);
        entidadCancelada.setCreatedAt(LocalDateTime.now());

        when(reservaRepository.save(any())).thenReturn(entidadCancelada);

        ReservaResponseDTO resultado = reservaService.cancelar(1L);

        assertThat(resultado.getEstado()).isEqualTo("CANCELADA");
        verify(reservaRepository).save(any(ReservaEntity.class));
    }

    @Test
    @DisplayName("cancelar() - debe lanzar excepción cuando reserva ya está COMPLETADA")
    void cancelar_debeLanzarExcepcion_cuandoReservaCompletada() {
        entidadGuardada.setEstado(ReservaEntity.EstadoReserva.COMPLETADA);
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));

        assertThatThrownBy(() -> reservaService.cancelar(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No se puede cancelar");
    }

    @Test
    @DisplayName("cancelar() - debe lanzar excepción cuando reserva ya está CANCELADA")
    void cancelar_debeLanzarExcepcion_cuandoReservaYaCancelada() {

        entidadGuardada.setEstado(ReservaEntity.EstadoReserva.CANCELADA);
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));

        assertThatThrownBy(() -> reservaService.cancelar(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No se puede cancelar");
    }
    // TESTS: listar() y listarPorUsuario()

    @Test
    @DisplayName("listar() - debe retornar lista de todas las reservas")
    void listar_debeRetornarTodasLasReservas() {

        when(reservaRepository.findAll()).thenReturn(List.of(entidadGuardada));
        List<ReservaResponseDTO> resultado = reservaService.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("listarPorUsuario() - debe retornar reservas del usuario indicado")
    void listarPorUsuario_debeRetornarReservasDelUsuario() {

        when(reservaRepository.findByUsuarioId(1L)).thenReturn(List.of(entidadGuardada));
        List<ReservaResponseDTO> resultado = reservaService.listarPorUsuario(1L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getUsuarioId()).isEqualTo(1L);
    }
}
