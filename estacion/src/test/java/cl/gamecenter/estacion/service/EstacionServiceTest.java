package cl.gamecenter.estacion.service;

import cl.gamecenter.estacion.dto.EstacionRequestDTO;
import cl.gamecenter.estacion.dto.EstacionResponseDTO;
import cl.gamecenter.estacion.entity.EstacionEntity;
import cl.gamecenter.estacion.entity.TipoEstacionEntity;
import cl.gamecenter.estacion.repository.EstacionRepository;
import cl.gamecenter.estacion.repository.TipoEstacionRepository;
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
class EstacionServiceTest {

    @Mock
    private EstacionRepository estacionRepository;

    @Mock
    private TipoEstacionRepository tipoEstacionRepository;

    @InjectMocks
    private EstacionService estacionService;

    private EstacionRequestDTO requestDTO;
    private TipoEstacionEntity tipoEstacion;
    private EstacionEntity entidadGuardada;

    @BeforeEach
    void setUp() {
        requestDTO = new EstacionRequestDTO();
        requestDTO.setNombre("PC Gaming 1");
        requestDTO.setEspecificaciones("RTX 4080, 32GB RAM");
        requestDTO.setTipoEstacionId(1L);

        tipoEstacion = new TipoEstacionEntity();
        tipoEstacion.setId(1L);
        tipoEstacion.setNombre("PC Gaming");
        tipoEstacion.setPrecioHora(new BigDecimal("3000.00"));
        tipoEstacion.setActivo(true);

        entidadGuardada = new EstacionEntity();
        entidadGuardada.setId(1L);
        entidadGuardada.setNombre("PC Gaming 1");
        entidadGuardada.setEspecificaciones("RTX 4080, 32GB RAM");
        entidadGuardada.setTipoEstacion(tipoEstacion);
        entidadGuardada.setEstado(EstacionEntity.EstadoEstacion.DISPONIBLE);
        entidadGuardada.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("crear() - debe crear estación cuando el tipo existe")
    void crear_debeCrearEstacion_cuandoTipoExiste() {
        when(tipoEstacionRepository.findById(1L)).thenReturn(Optional.of(tipoEstacion));
        when(estacionRepository.save(any(EstacionEntity.class))).thenReturn(entidadGuardada);

        EstacionResponseDTO resultado = estacionService.crear(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getEstado()).isEqualTo("DISPONIBLE");
        assertThat(resultado.getTipoEstacionNombre()).isEqualTo("PC Gaming");
        verify(estacionRepository).save(any(EstacionEntity.class));
    }

    @Test
    @DisplayName("crear() - debe lanzar excepción cuando tipo de estación no existe")
    void crear_debeLanzarExcepcion_cuandoTipoNoExiste() {
        when(tipoEstacionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> estacionService.crear(requestDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Tipo de estacion no encontrado");

        verify(estacionRepository, never()).save(any());
    }

    @Test
    @DisplayName("buscarPorId() - debe retornar estación cuando existe")
    void buscarPorId_debeRetornarEstacion_cuandoExiste() {
        when(estacionRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));

        EstacionResponseDTO resultado = estacionService.buscarPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("buscarPorId() - debe lanzar excepción cuando no existe")
    void buscarPorId_debeLanzarExcepcion_cuandoNoExiste() {
        when(estacionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> estacionService.buscarPorId(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Estacion no encontrada");
    }

    @Test
    @DisplayName("listarDisponibles() - debe retornar estaciones en estado DISPONIBLE")
    void listarDisponibles_debeRetornarEstacionesDisponibles() {
        when(estacionRepository.findByEstado(EstacionEntity.EstadoEstacion.DISPONIBLE))
                .thenReturn(List.of(entidadGuardada));

        List<EstacionResponseDTO> resultado = estacionService.listarDisponibles();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getEstado()).isEqualTo("DISPONIBLE");
    }

    @Test
    @DisplayName("cambiarEstado() - debe actualizar el estado de la estación")
    void cambiarEstado_debeActualizarEstado() {
        when(estacionRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));
        entidadGuardada.setEstado(EstacionEntity.EstadoEstacion.OCUPADA);
        when(estacionRepository.save(any(EstacionEntity.class))).thenReturn(entidadGuardada);

        EstacionResponseDTO resultado = estacionService.cambiarEstado(1L, "OCUPADA");

        assertThat(resultado.getEstado()).isEqualTo("OCUPADA");
        verify(estacionRepository).save(any(EstacionEntity.class));
    }

    @Test
    @DisplayName("listar() - debe retornar todas las estaciones")
    void listar_debeRetornarTodasLasEstaciones() {
        when(estacionRepository.findAll()).thenReturn(List.of(entidadGuardada));

        List<EstacionResponseDTO> resultado = estacionService.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("PC Gaming 1");
    }
}
