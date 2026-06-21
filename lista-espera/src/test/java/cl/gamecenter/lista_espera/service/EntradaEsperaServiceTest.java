package cl.gamecenter.lista_espera.service;

import cl.gamecenter.lista_espera.client.NotificacionClient;
import cl.gamecenter.lista_espera.client.TipoEstacionClient;
import cl.gamecenter.lista_espera.client.UsuarioClient;
import cl.gamecenter.lista_espera.dto.*;
import cl.gamecenter.lista_espera.entity.EntradaEsperaEntity;
import cl.gamecenter.lista_espera.entity.EntradaEsperaEntity.EstadoEspera;
import cl.gamecenter.lista_espera.repository.EntradaEsperaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntradaEsperaServiceTest {

    @Mock private EntradaEsperaRepository entradaEsperaRepository;
    @Mock private UsuarioClient usuarioClient;
    @Mock private TipoEstacionClient tipoEstacionClient;
    @Mock private NotificacionClient notificacionClient;

    @InjectMocks
    private EntradaEsperaService entradaEsperaService;

    private EntradaEsperaRequestDTO requestDTO;
    private EntradaEsperaEntity entidadGuardada;
    private UsuarioClientDTO usuarioActivo;
    private TipoEstacionClientDTO tipoActivo;

    @BeforeEach
    void setUp() {
        // Request base — estado ESPERANDO
        requestDTO = new EntradaEsperaRequestDTO();
        requestDTO.setUsuarioId(1L);
        requestDTO.setTipoEstacionId(2L);
        requestDTO.setPosicion(1);
        requestDTO.setEstado("ESPERANDO");
        requestDTO.setFechaIngreso(LocalDateTime.now());

        // Usuario activo simulado
        usuarioActivo = new UsuarioClientDTO();
        usuarioActivo.setId(1L);
        usuarioActivo.setActivo(true);

        // Tipo estacion activo simulado
        tipoActivo = new TipoEstacionClientDTO();
        tipoActivo.setId(2L);
        tipoActivo.setNombre("PC Gaming");
        tipoActivo.setActivo(true);

        // Entidad guardada simulada
        entidadGuardada = new EntradaEsperaEntity();
        entidadGuardada.setId(1L);
        entidadGuardada.setUsuarioId(1L);
        entidadGuardada.setTipoEstacionId(2L);
        entidadGuardada.setPosicion(1);
        entidadGuardada.setEstado(EstadoEspera.ESPERANDO);
        entidadGuardada.setFechaIngreso(LocalDateTime.now());
    }

    // TESTS: crear()
    @Test
    @DisplayName("crear() - debe crear entrada cuando usuario y tipo estación están activos")
    void crear_debeCrearEntrada_cuandoUsuarioYTipoActivos() {
        when(usuarioClient.buscarPorId(1L)).thenReturn(usuarioActivo);
        when(tipoEstacionClient.buscarPorId(2L)).thenReturn(tipoActivo);
        when(entradaEsperaRepository.save(any(EntradaEsperaEntity.class))).thenReturn(entidadGuardada);


        EntradaEsperaResponseDTO resultado = entradaEsperaService.crear(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getUsuarioId()).isEqualTo(1L);
        assertThat(resultado.getEstado()).isEqualTo("ESPERANDO");
        verify(entradaEsperaRepository).save(any(EntradaEsperaEntity.class));
        verify(notificacionClient, never()).crear(any());
    }

    @Test
    @DisplayName("crear() - debe enviar notificación cuando estado es NOTIFICADO")
    void crear_debeEnviarNotificacion_cuandoEstadoEsNotificado() {
        // GIVEN
        requestDTO.setEstado("NOTIFICADO");
        entidadGuardada.setEstado(EstadoEspera.NOTIFICADO);

        when(usuarioClient.buscarPorId(1L)).thenReturn(usuarioActivo);
        when(tipoEstacionClient.buscarPorId(2L)).thenReturn(tipoActivo);
        when(entradaEsperaRepository.save(any(EntradaEsperaEntity.class))).thenReturn(entidadGuardada);

        EntradaEsperaResponseDTO resultado = entradaEsperaService.crear(requestDTO);

        assertThat(resultado.getEstado()).isEqualTo("NOTIFICADO");
        verify(notificacionClient).crear(any(NotificacionClientDTO.class));
    }

    @Test
    @DisplayName("crear() - debe lanzar excepción cuando usuario no está activo")
    void crear_debeLanzarExcepcion_cuandoUsuarioNoActivo() {
        usuarioActivo.setActivo(false);
        when(usuarioClient.buscarPorId(1L)).thenReturn(usuarioActivo);

        assertThatThrownBy(() -> entradaEsperaService.crear(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no está activo");

        verify(entradaEsperaRepository, never()).save(any());
    }

    @Test
    @DisplayName("crear() - debe lanzar excepción cuando tipo de estación no está activo")
    void crear_debeLanzarExcepcion_cuandoTipoEstacionNoActivo() {
        tipoActivo.setActivo(false);
        when(usuarioClient.buscarPorId(1L)).thenReturn(usuarioActivo);
        when(tipoEstacionClient.buscarPorId(2L)).thenReturn(tipoActivo);

        assertThatThrownBy(() -> entradaEsperaService.crear(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("no está activo");

        verify(entradaEsperaRepository, never()).save(any());
    }


    // TESTS: obtenerPorId()
    @Test
    @DisplayName("obtenerPorId() - debe retornar entrada cuando existe")
    void obtenerPorId_debeRetornarEntrada_cuandoExiste() {
        when(entradaEsperaRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));
        EntradaEsperaResponseDTO resultado = entradaEsperaService.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getEstado()).isEqualTo("ESPERANDO");
    }

    @Test
    @DisplayName("obtenerPorId() - debe lanzar excepción cuando no existe")
    void obtenerPorId_debeLanzarExcepcion_cuandoNoExiste() {
        when(entradaEsperaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> entradaEsperaService.obtenerPorId(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("no encontrada");
    }

    // TESTS: listar()
    @Test
    @DisplayName("listar() - debe retornar todas las entradas")
    void listar_debeRetornarTodasLasEntradas() {
        when(entradaEsperaRepository.findAll()).thenReturn(List.of(entidadGuardada));
        List<EntradaEsperaResponseDTO> resultado = entradaEsperaService.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
    }

    // TESTS: eliminar()
    @Test
    @DisplayName("eliminar() - debe eliminar entrada cuando existe")
    void eliminar_debeEliminarEntrada_cuandoExiste() {
        when(entradaEsperaRepository.existsById(1L)).thenReturn(true);
        entradaEsperaService.eliminar(1L);
        verify(entradaEsperaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar() - debe lanzar excepción cuando entrada no existe")
    void eliminar_debeLanzarExcepcion_cuandoNoExiste() {
        when(entradaEsperaRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> entradaEsperaService.eliminar(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("no encontrada");

        verify(entradaEsperaRepository, never()).deleteById(any());
    }
}