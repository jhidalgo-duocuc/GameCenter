package cl.gamecenter.reporte.service;

import cl.gamecenter.reporte.client.EstacionClient;
import cl.gamecenter.reporte.dto.EstacionClientDTO;
import cl.gamecenter.reporte.dto.ReporteOcupacionRequestDTO;
import cl.gamecenter.reporte.dto.ReporteOcupacionResponseDTO;
import cl.gamecenter.reporte.entity.ReporteOcupacionEntity;
import cl.gamecenter.reporte.repository.ReporteOcupacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReporteOcupacionServiceTest {

    @Mock private ReporteOcupacionRepository reporteOcupacionRepository;
    @Mock private EstacionClient estacionClient;

    @InjectMocks
    private ReporteOcupacionService reporteOcupacionService;

    private ReporteOcupacionRequestDTO requestDTO;
    private ReporteOcupacionEntity entidadGuardada;
    private EstacionClientDTO estacionDTO;

    @BeforeEach
    void setUp() {
        requestDTO = new ReporteOcupacionRequestDTO();
        requestDTO.setEstacionId(1L);
        requestDTO.setFecha(LocalDate.now());
        requestDTO.setHorasOcupadas(new BigDecimal("6.00"));
        requestDTO.setHorasDisponibles(new BigDecimal("8.00"));
        requestDTO.setPctOcupacion(new BigDecimal("75.00"));
        requestDTO.setIngresosDia(new BigDecimal("18000.00"));

        estacionDTO = new EstacionClientDTO();
        estacionDTO.setId(1L);
        estacionDTO.setNombre("PC Gaming 1");
        estacionDTO.setEstado("DISPONIBLE");

        entidadGuardada = new ReporteOcupacionEntity();
        entidadGuardada.setId(1L);
        entidadGuardada.setEstacionId(1L);
        entidadGuardada.setFecha(LocalDate.now());
        entidadGuardada.setHorasOcupadas(new BigDecimal("6.00"));
        entidadGuardada.setHorasDisponibles(new BigDecimal("8.00"));
        entidadGuardada.setPctOcupacion(new BigDecimal("75.00"));
        entidadGuardada.setIngresosDia(new BigDecimal("18000.00"));
    }

    // TESTS: crear()
    @Test
    @DisplayName("crear() - debe crear reporte cuando la estación existe")
    void crear_debeCrearReporte_cuandoEstacionExiste() {
        when(estacionClient.buscarPorId(1L)).thenReturn(estacionDTO);
        when(reporteOcupacionRepository.save(any(ReporteOcupacionEntity.class))).thenReturn(entidadGuardada);

        ReporteOcupacionResponseDTO resultado = reporteOcupacionService.crear(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getEstacionId()).isEqualTo(1L);
        assertThat(resultado.getPctOcupacion()).isEqualByComparingTo(new BigDecimal("75.00"));
        verify(estacionClient).buscarPorId(1L);
        verify(reporteOcupacionRepository).save(any(ReporteOcupacionEntity.class));
    }

    @Test
    @DisplayName("crear() - debe lanzar excepción cuando estación no existe via Feign")
    void crear_debeLanzarExcepcion_cuandoEstacionNoExiste() {
        when(estacionClient.buscarPorId(99L)).thenThrow(new RuntimeException("Estacion no encontrada"));
        requestDTO.setEstacionId(99L);

        assertThatThrownBy(() -> reporteOcupacionService.crear(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Estacion no encontrada");

        verify(reporteOcupacionRepository, never()).save(any());
    }

    // TESTS: obtenerPorId()
    @Test
    @DisplayName("obtenerPorId() - debe retornar reporte cuando existe")
    void obtenerPorId_debeRetornarReporte_cuandoExiste() {
        when(reporteOcupacionRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));

        ReporteOcupacionResponseDTO resultado = reporteOcupacionService.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getIngresosDia()).isEqualByComparingTo(new BigDecimal("18000.00"));
    }

    @Test
    @DisplayName("obtenerPorId() - debe lanzar excepción cuando no existe")
    void obtenerPorId_debeLanzarExcepcion_cuandoNoExiste() {
        when(reporteOcupacionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reporteOcupacionService.obtenerPorId(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("no encontrado");
    }

    // TESTS: listar()
    @Test
    @DisplayName("listar() - debe retornar todos los reportes")
    void listar_debeRetornarTodosLosReportes() {
        when(reporteOcupacionRepository.findAll()).thenReturn(List.of(entidadGuardada));
        List<ReporteOcupacionResponseDTO> resultado = reporteOcupacionService.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getEstacionId()).isEqualTo(1L);
    }

    // TESTS: eliminar()

    @Test
    @DisplayName("eliminar() - debe eliminar reporte cuando existe")
    void eliminar_debeEliminarReporte_cuandoExiste() {
        when(reporteOcupacionRepository.existsById(1L)).thenReturn(true);

        reporteOcupacionService.eliminar(1L);
        verify(reporteOcupacionRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar() - debe lanzar excepción cuando no existe")
    void eliminar_debeLanzarExcepcion_cuandoNoExiste() {
        // GIVEN
        when(reporteOcupacionRepository.existsById(99L)).thenReturn(false);

        // WHEN & THEN
        assertThatThrownBy(() -> reporteOcupacionService.eliminar(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("no encontrado");

        verify(reporteOcupacionRepository, never()).deleteById(any());
    }
}