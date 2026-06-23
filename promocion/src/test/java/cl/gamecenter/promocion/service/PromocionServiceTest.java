package cl.gamecenter.promocion.service;

import cl.gamecenter.promocion.dto.PromocionRequestDTO;
import cl.gamecenter.promocion.dto.PromocionResponseDTO;
import cl.gamecenter.promocion.entity.PromocionEntity;
import cl.gamecenter.promocion.entity.PromocionEntity.TipoPromocion;
import cl.gamecenter.promocion.repository.PromocionRepository;
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
class PromocionServiceTest {

    @Mock
    private PromocionRepository promocionRepository;

    @InjectMocks
    private PromocionService promocionService;

    private PromocionRequestDTO requestDTO;
    private PromocionEntity entidadGuardada;

    @BeforeEach
    void setUp() {
        requestDTO = new PromocionRequestDTO();
        requestDTO.setNombre("Verano Gamer");
        requestDTO.setDescripcion("20% de descuento en sesiones");
        requestDTO.setTipo("PORCENTAJE");
        requestDTO.setDescuentoPct(new BigDecimal("20.00"));
        requestDTO.setFechaInicio(LocalDate.now());
        requestDTO.setFechaFin(LocalDate.now().plusMonths(1));
        requestDTO.setActivo(true);

        entidadGuardada = new PromocionEntity();
        entidadGuardada.setId(1L);
        entidadGuardada.setNombre("Verano Gamer");
        entidadGuardada.setDescripcion("20% de descuento en sesiones");
        entidadGuardada.setTipo(TipoPromocion.PORCENTAJE);
        entidadGuardada.setDescuentoPct(new BigDecimal("20.00"));
        entidadGuardada.setFechaInicio(requestDTO.getFechaInicio());
        entidadGuardada.setFechaFin(requestDTO.getFechaFin());
        entidadGuardada.setActivo(true);
    }

    @Test
    @DisplayName("crear() - debe crear promoción con datos válidos")
    void crear_debeCrearPromocion_conDatosValidos() {
        when(promocionRepository.save(any(PromocionEntity.class))).thenReturn(entidadGuardada);

        PromocionResponseDTO resultado = promocionService.crear(requestDTO);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Verano Gamer");
        assertThat(resultado.getTipo()).isEqualTo("PORCENTAJE");
        verify(promocionRepository).save(any(PromocionEntity.class));
    }

    @Test
    @DisplayName("obtenerPorId() - debe retornar promoción cuando existe")
    void obtenerPorId_debeRetornarPromocion_cuandoExiste() {
        when(promocionRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));

        PromocionResponseDTO resultado = promocionService.obtenerPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getActivo()).isTrue();
    }

    @Test
    @DisplayName("obtenerPorId() - debe lanzar excepción cuando no existe")
    void obtenerPorId_debeLanzarExcepcion_cuandoNoExiste() {
        when(promocionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> promocionService.obtenerPorId(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Promocion no encontrada");
    }

    @Test
    @DisplayName("listar() - debe retornar todas las promociones")
    void listar_debeRetornarTodasLasPromociones() {
        when(promocionRepository.findAll()).thenReturn(List.of(entidadGuardada));

        List<PromocionResponseDTO> resultado = promocionService.listar();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("actualizar() - debe actualizar promoción cuando existe")
    void actualizar_debeActualizarPromocion_cuandoExiste() {
        when(promocionRepository.findById(1L)).thenReturn(Optional.of(entidadGuardada));
        when(promocionRepository.save(any(PromocionEntity.class))).thenReturn(entidadGuardada);

        PromocionResponseDTO resultado = promocionService.actualizar(1L, requestDTO);

        assertThat(resultado).isNotNull();
        verify(promocionRepository).save(any(PromocionEntity.class));
    }

    @Test
    @DisplayName("eliminar() - debe eliminar promoción cuando existe")
    void eliminar_debeEliminarPromocion_cuandoExiste() {
        when(promocionRepository.existsById(1L)).thenReturn(true);

        promocionService.eliminar(1L);

        verify(promocionRepository).deleteById(1L);
    }

    @Test
    @DisplayName("eliminar() - debe lanzar excepción cuando promoción no existe")
    void eliminar_debeLanzarExcepcion_cuandoNoExiste() {
        when(promocionRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> promocionService.eliminar(99L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Promocion no encontrada");

        verify(promocionRepository, never()).deleteById(any());
    }
}
