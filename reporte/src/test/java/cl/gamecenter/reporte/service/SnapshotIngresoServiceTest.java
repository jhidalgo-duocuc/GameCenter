package cl.gamecenter.reporte.service;

import cl.gamecenter.reporte.dto.SnapshotIngresoRequestDTO;
import cl.gamecenter.reporte.dto.SnapshotIngresoResponseDTO;
import cl.gamecenter.reporte.entity.SnapshotIngresoEntity;
import cl.gamecenter.reporte.repository.SnapshotIngresoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SnapshotIngresoServiceTest {

    @Mock
    private SnapshotIngresoRepository snapshotIngresoRepository;

    @InjectMocks
    private SnapshotIngresoService snapshotIngresoService;

    private SnapshotIngresoRequestDTO requestDTO;
    private SnapshotIngresoEntity entidadGuardada;

    @BeforeEach
    void setUp() {
        requestDTO = new SnapshotIngresoRequestDTO();
        requestDTO.setPeriodo("2026-06");
        requestDTO.setTotalSesiones(150);
        requestDTO.setTotalMembresias(30);
        requestDTO.setIngresosBrutos(new BigDecimal("450000.00"));
        requestDTO.setDescuentosTotal(new BigDecimal("45000.00"));
        requestDTO.setIngresosNetos(new BigDecimal("405000.00"));
        requestDTO.setGeneratedAt(LocalDateTime.now());

        entidadGuardada = new SnapshotIngresoEntity();
        entidadGuardada.setId(1L);
        entidadGuardada.setPeriodo("2026-06");
        entidadGuardada.setTotalSesiones(150);
        entidadGuardada.setTotalMembresias(30);
        entidadGuardada.setIngresosBrutos(new BigDecimal("450000.00"));
        entidadGuardada.setDescuentosTotal(new BigDecimal("45000.00"));
        entidadGuardada.setIngresosNetos(new BigDecimal("405000.00"));
        entidadGuardada.setGeneratedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("crear() - debe crear snapshot de ingreso correctamente")
    void crear_debeCrearSnapshot() {
        when(snapshotIngresoRepository.save(any(SnapshotIngresoEntity.class))).thenReturn(entidadGuardada);

        SnapshotIngresoResponseDTO resultado = snapshotIngresoService.crear(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getPeriodo()).isEqualTo("2026-06");
        assertThat(resultado.getIngresosNetos()).isEqualByComparingTo(new BigDecimal("405000.00"));
        verify(snapshotIngresoRepository).save(any(SnapshotIngresoEntity.class));
    }

    @Test
    @DisplayName("obtenerPorId() - debe retornar snapshot cuando existe")
    void obtenerPorId_debeRetornarSnapshot_cuandoExiste() {
        when(snapshotIngresoRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));

        SnapshotIngresoResponseDTO resultado = snapshotIngresoService.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getTotalSesiones()).isEqualTo(150);
        assertThat(resultado.getTotalMembresias()).isEqualTo(30);
    }

    @Test
    @DisplayName("obtenerPorId() - debe lanzar excepción cuando no existe")
    void obtenerPorId_debeLanzarExcepcion_cuandoNoExiste() {
        when(snapshotIngresoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> snapshotIngresoService.obtenerPorId(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("no encontrado");
    }

    @Test
    @DisplayName("listar() - debe retornar todos los snapshots")
    void listar_debeRetornarTodosLosSnapshots() {
        when(snapshotIngresoRepository.findAll()).thenReturn(List.of(entidadGuardada));

        List<SnapshotIngresoResponseDTO> resultado = snapshotIngresoService.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getPeriodo()).isEqualTo("2026-06");
    }

    @Test
    @DisplayName("eliminar() - debe eliminar snapshot cuando existe")
    void eliminar_debeEliminarSnapshot_cuandoExiste() {
        when(snapshotIngresoRepository.existsById(1L)).thenReturn(true);

        snapshotIngresoService.eliminar(1L);

        verify(snapshotIngresoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar() - debe lanzar excepción cuando no existe")
    void eliminar_debeLanzarExcepcion_cuandoNoExiste() {
        when(snapshotIngresoRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> snapshotIngresoService.eliminar(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("no encontrado");

        verify(snapshotIngresoRepository, never()).deleteById(any());
    }
}